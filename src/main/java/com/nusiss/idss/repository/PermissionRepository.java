package com.nusiss.idss.repository;

import com.nusiss.idss.po.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
