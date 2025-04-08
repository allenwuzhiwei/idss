package com.nusiss.idss.service;

import com.nusiss.idss.po.Permission;
import com.nusiss.idss.po.RolePermission;
import com.nusiss.idss.po.User;
import com.nusiss.idss.po.UserRole;
import com.nusiss.idss.repository.PermissionRepository;
import com.nusiss.idss.repository.RolePermissionRepository;
import com.nusiss.idss.repository.UserRepository;
import com.nusiss.idss.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserPermissionService {

    @Autowired
    private UserRepository userRepository;
    @Autowired private UserRoleRepository userRoleRepo;
    @Autowired private RolePermissionRepository rolePermissionRepo;
    @Autowired private PermissionRepository permissionRepo;
    @Autowired private RedisTemplate<String, Set<String>> redisTemplate;

    /*public Set<String> getUserPermissions(String username) {
        ValueOperations<String, Set<String>> ops = redisTemplate.opsForValue();
        if (ops.get(username) != null) {
            return ops.get(username);
        }

        Optional<User> userO = userRepository.findByUsername(username);
        if(userO.isPresent()){
            User user = userO.get();
            List<UserRole> userRoles = userRoleRepo.findByUserId(user.getUserId());

            Set<String> permissions = new HashSet<>();
            for (UserRole ur : userRoles) {
                List<RolePermission> rolePermissions = rolePermissionRepo.findByRoleId(ur.getRoleId());
                for (RolePermission rp : rolePermissions) {
                    Permission p = permissionRepo.findByPermissionId(rp.getPermissionId());
                    permissions.add(p.getPermissionName());
                }
            }
            ops.set(username, permissions);
            return permissions;
        }

        return null;

    }*/
}
