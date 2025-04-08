package com.nusiss.idss.repository;

import com.nusiss.idss.po.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {

    List<RolePermission> findByRoleId(Integer roleId);
}
