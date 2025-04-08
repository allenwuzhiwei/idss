package com.nusiss.idss.repository;

import com.nusiss.idss.po.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Permission findByPermissionId(Integer permissionId);

    @Query(value = """
    SELECT DISTINCT p.permission_name
    FROM users u
    LEFT JOIN user_roles ur ON u.user_id = ur.user_id
    LEFT JOIN role_permissions rp ON ur.role_id = rp.role_id
    LEFT JOIN permissions p ON rp.permission_id = p.permission_id
    WHERE u.username = :username 
    """, nativeQuery = true)
    List<String> findPermissionsByUsername(@Param("username") String username);

    @Query(value = """
    SELECT CASE 
             WHEN COUNT(*) > 0 THEN true 
             ELSE false 
           END
    FROM users u
    LEFT JOIN user_roles ur ON u.user_id = ur.user_id
    LEFT JOIN role_permissions rp ON ur.role_id = rp.role_id
    LEFT JOIN permissions p ON rp.permission_id = p.permission_id
    WHERE u.username = :username AND p.permission_name = :permissionKey
    """, nativeQuery = true)
    boolean userHasPermission(@Param("username") String username, @Param("permissionKey") String permissionKey);
}
