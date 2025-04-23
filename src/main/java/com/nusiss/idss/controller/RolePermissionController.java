package com.nusiss.idss.controller;

import com.nusiss.idss.config.ApiResponse;
import com.nusiss.idss.po.Permission;
import com.nusiss.idss.po.Role;
import com.nusiss.idss.po.User;
import com.nusiss.idss.repository.PermissionRepository;
import com.nusiss.idss.repository.RolePermissionRepository;
import com.nusiss.idss.repository.RoleRepository;
import com.nusiss.idss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
