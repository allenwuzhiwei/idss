package com.nusiss.idss.controller;

import com.nusiss.idss.config.ApiResponse;
import com.nusiss.idss.po.Device;
import com.nusiss.idss.service.DeviceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Device>>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices();
        return ResponseEntity.ok(new ApiResponse<>(true, "Devices retrieved successfully", devices));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Device>> getDeviceById(@PathVariable Integer id) {
        Optional<Device> device = deviceService.getDeviceById(id);
        return device
                .map(value -> ResponseEntity.ok(new ApiResponse<>(true, "Device found", value)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Device not found", null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Device>> createDevice(@RequestBody Device device, HttpServletRequest request) {
        Device createdDevice = deviceService.createDevice(device, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Device created successfully", createdDevice));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Device>> updateDevice(@PathVariable Integer id, @RequestBody Device device) {
        try {
            Device updatedDevice = deviceService.updateDevice(id, device);
            return ResponseEntity.ok(new ApiResponse<>(true, "Device updated successfully", updatedDevice));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDevice(@PathVariable Integer id) {
        deviceService.deleteDevice(id);

        return ResponseEntity.ok(new ApiResponse<>(true, "Device deleted successfully", null));
    }
}
