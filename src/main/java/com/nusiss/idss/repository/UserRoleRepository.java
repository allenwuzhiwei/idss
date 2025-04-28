package com.nusiss.idss.repository;

import com.nusiss.idss.po.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository  extends JpaRepository<UserRole, String> {

    UserRole findByUserId(Integer userId);

    Integer deleteByUserId(Integer userID);
}
