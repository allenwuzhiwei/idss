package com.nusiss.idss.repository;

import com.nusiss.idss.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, String> {
}
