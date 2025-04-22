package com.nusiss.idss.controller;

import com.nusiss.idss.config.ApiResponse;
import com.nusiss.idss.po.Permission;
import com.nusiss.idss.po.User;
import com.nusiss.idss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Permission>>> getAllUsers() {
        List<Permission> permissions = service.getAllPermissions();
        return ResponseEntity.ok(new ApiResponse<>(true, "Permissions retrieved successfully", permissions));
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<ApiResponse<List<Permission>>> findPermissionsByRoleId(@PathVariable Integer roleId){
        List<Permission> permissions = service.findPermissionsByRoleId(roleId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Permissions retrieved successfully", permissions));
    }

}
