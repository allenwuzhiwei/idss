package com.nusiss.idss.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;

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
}