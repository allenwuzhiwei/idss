package com.nusiss.idss.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusiss.idss.po.User;
import com.nusiss.idss.repository.UserRepository;
import com.nusiss.idss.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class S3Service {

    private final String bucketName = "intruder-images-bucket"; // Your S3 bucket name

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKeyId;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretAccessKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    private final S3Presigner s3Presigner;

    private final String folder = "image-whitelist/";

    @Autowired
    private S3Client s3Client;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    public S3Service(@Value("${cloud.aws.credentials.access-key}") String accessKeyId,
                     @Value("${cloud.aws.credentials.secret-key}") String secretAccessKey,
                     @Value("${cloud.aws.region.static}") String region) {
        this.s3Presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();
    }

    public String generatePresignedUrl(String objectKey) {
        // Create a GetObjectRequest with the bucket and object key
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        // Create a request to presign the object for getting it from S3
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))  // Valid for 10 minutes
                .build();

        // Generate the pre-signed URL using the S3Presigner
        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);

        // Return the URL as a string
        return presignedRequest.url().toString();
    }

    public Map<String, String> uploadFileToS3(MultipartFile file, HttpServletRequest request){
        try {
            String key = folder + UUID.randomUUID() + "_" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    //.acl(ObjectCannedACL.PUBLIC_READ) // make it public (optional)
                    .build();

            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            User user = jwtUtil.getCurrentUserInfo(request);
            user.setProfilePictureUrl(key);
            user.setProfilePictureUpdateDatetime(LocalDateTime.now());
            //set to 0, which means the image will be verified again by edge side.
            //if it is the first, also make sense.
            user.setStatusCode("0");
            userRepository.save(user);
            String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + key;
            String presignedUrl = generatePresignedUrl(key);

            Map<String, String> payload = new HashMap<>();
            payload.put("url", presignedUrl);
            //String payloadString = objectMapper.writeValueAsString(payload);
            //awsIotMqttPublisher.publish(payloadString);
            //return Map.of("url", presignedUrl);\
            return payload;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}