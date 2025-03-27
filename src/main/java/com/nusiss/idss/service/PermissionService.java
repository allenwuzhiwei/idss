package com.nusiss.idss.service;

import com.nusiss.idss.po.Permission;
import com.nusiss.idss.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository repository;

    public List<Permission> getAllPermissions() {
        return repository.findAll();
    }

    public Optional<Permission> getPermissionById(String id) {
        return repository.findById(id);
    }

    public Permission createPermission(Permission permission) {
        return repository.save(permission);
    }

    public Permission updatePermission(String id, Permission permission) {
        if (repository.existsById(id)) {
            permission.setPermissionId(id);
            return repository.save(permission);
        }
        return null;
    }

    public boolean deletePermission(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
