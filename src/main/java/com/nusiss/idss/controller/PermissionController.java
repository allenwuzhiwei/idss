package com.nusiss.idss.controller;

import com.nusiss.idss.po.Permission;
import com.nusiss.idss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @GetMapping
    public List<Permission> getAllPermissions() {
        return service.getAllPermissions();
    }
}
