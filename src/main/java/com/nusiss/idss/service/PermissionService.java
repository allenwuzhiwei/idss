package com.nusiss.idss.service;

import com.nusiss.idss.po.Permission;
import com.nusiss.idss.po.RolePermission;
import com.nusiss.idss.repository.PermissionRepository;
import com.nusiss.idss.repository.RolePermissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository repository;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Permission> findPermissionsByRoleId(Integer roleId){

        if (roleId == 1){
            //getAllApiEndpoints();
        }
        return repository.findPermissionsByRoleId(roleId);
    }
    public List<String> getAllApiEndpoints() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        List<String> endpoints = new ArrayList<>();

        for (RequestMappingInfo info : handlerMethods.keySet()) {
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();

            for (String pattern : patterns) {
                if (methods.isEmpty()) {
                    // Default to all methods if none specified
                    endpoints.add("ALL:" + pattern);
                } else {
                    for (RequestMethod method : methods) {
                        endpoints.add(method.name() + ":" + pattern);
                    }
                }
            }
        }

        return endpoints;
    }

    public List<Permission> getAllPermissions() {
        return repository.findAll();
    }

    public Optional<Permission> getPermissionById(Integer id) {
        return repository.findById(id);
    }

    public Permission createPermission(Permission permission) {
        return repository.save(permission);
    }

    public Permission updatePermission(Integer id, Permission permission) {
        if (repository.existsById(id)) {
            permission.setPermissionId(id);
            return repository.save(permission);
        }
        return null;
    }

    public boolean deletePermission(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void assignPermissionsToRole(Integer roleId, List<Integer> permissionIds) {
        //delete roleId related permissions
        rolePermissionRepository.deleteByRoleId(roleId);
        /*List<RolePermission> rolePermissions = new ArrayList<>();

        for (Integer permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        }*/

        //rolePermissionRepository.saveAll(rolePermissions);

        bulkInsert(roleId, permissionIds);

    }

    public void bulkInsert(Integer roleId, List<Integer> permissionIds) {
        String sql = "INSERT INTO Role_Permissions (role_id, permission_id) VALUES (?, ?)";

        List<Object[]> batchArgs = permissionIds.stream()
                .map(pid -> new Object[]{roleId, pid})
                .toList();

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

}
