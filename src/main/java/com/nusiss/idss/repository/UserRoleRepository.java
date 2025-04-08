package com.nusiss.idss.repository;

import com.nusiss.idss.po.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository  extends JpaRepository<UserRole, String> {

    List<UserRole> findByUserId(Integer userId);
}
