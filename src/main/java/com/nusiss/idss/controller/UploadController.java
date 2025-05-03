package com.nusiss.idss.controller;

import com.nusiss.idss.dto.ImageSyncDTO;
import com.nusiss.idss.service.S3Service;
import com.nusiss.idss.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(s3Service.uploadFileToS3(file, request));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to upload image"));
        }
    }

    @GetMapping("/imageSync")
    public ResponseEntity<?> syncUserImages(HttpServletRequest request) {
        try {
            List<ImageSyncDTO> imageSyncDTOs = userService.syncUserImages(request);
            return ResponseEntity.ok(imageSyncDTOs);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("statusCode", 500);
            errorResponse.put("message", "Failed to sync user images: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/imageSync")
    public ResponseEntity<Map<String, Object>> updateImageStatus(
            @RequestBody List<ImageSyncDTO> imageSyncDTOs,
            HttpServletRequest request) {

        try {
            Map<String, Object> response = userService.updateUserImages(imageSyncDTOs, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("statusCode", 500);
            errorResponse.put("message", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}