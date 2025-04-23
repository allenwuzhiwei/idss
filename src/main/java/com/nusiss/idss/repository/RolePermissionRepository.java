package com.nusiss.idss.repository;

import com.nusiss.idss.po.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {

    @Modifying
    @Query(value = """ 
            delete from Role_Permissions where role_id = :roleId
            """, nativeQuery = true)
    void deleteByRoleId(@Param("roleId") Integer roleId);

    List<RolePermission> findByRoleId(Integer roleId);
}
