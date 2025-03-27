package com.nusiss.idss.controller;

import com.nusiss.idss.config.ApiResponse;
import com.nusiss.idss.po.Alert;
import com.nusiss.idss.service.AlertService;
import com.nusiss.idss.vo.AlertVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService service;

    // Get all alerts
    @GetMapping
    public ResponseEntity<ApiResponse<List<Alert>>> getAllAlerts() {
        List<Alert> alerts = service.getAllAlerts();
        if (alerts.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(false, "No alerts found", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Alerts retrieved successfully", alerts));
    }

    // Get alert by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Alert>> getAlertById(@PathVariable Integer id) {
        Optional<Alert> alert = service.getAlertById(id);
        return alert.map(a -> ResponseEntity.ok(new ApiResponse<>(true, "Alert found", a)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Alert not found", null)));
    }

    // Create new alert
    @PostMapping
    public ResponseEntity<ApiResponse<Alert>> createAlert(@RequestBody AlertVO alertVO) {
        Alert createdAlert = service.createAlert(alertVO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Alert created successfully", createdAlert));
    }

    // Update existing alert
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Alert>> updateAlert(@PathVariable Integer id, @RequestBody Alert alert) {
        Alert updatedAlert = service.updateAlert(id, alert);
        return ResponseEntity.ok(new ApiResponse<>(true, "Alert updated successfully", updatedAlert));
    }

    // Delete alert
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAlert(@PathVariable Integer id) {
        boolean isDeleted = service.deleteAlert(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Alert deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, "Alert not found", null));
    }
}
