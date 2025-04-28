package com.nusiss.idss.controller;

import com.nusiss.idss.config.ApiResponse;
import com.nusiss.idss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> assignPermissionsToRole(@RequestBody Map<String, Object> request) {
        Integer roleId = Integer.valueOf(request.get("roleId").toString());
        List<Integer> permissionIdsRaw = (List<Integer>) request.get("permissionIds");
        permissionService.assignPermissionsToRole(roleId,permissionIdsRaw);

        return ResponseEntity.ok(new ApiResponse<>(true, "Permissions assigned to role successfully.", ""));
    }

}
