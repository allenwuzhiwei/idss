package com.nusiss.idss.repository;

import com.nusiss.idss.dto.ImageSyncDTO;
import com.nusiss.idss.dto.UserDTO;
import com.nusiss.idss.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository  extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

    @Query(
            value = "SELECT new com.nusiss.idss.dto.UserDTO( " +
                    "u.userId, u.username, u.email, u.phoneNumber, u.status, u.createDatetime, u.createUser, r.roleName, r.roleId) " +
                    "FROM User u " +
                    "LEFT JOIN UserRole ur ON u.userId = ur.userId " +
                    "LEFT JOIN Role r ON ur.roleId = r.roleId " +
                    "WHERE (:username IS NULL OR :username = '' OR u.username = :username)",
            countQuery = "SELECT COUNT(u) " +
                    "FROM User u " +
                    "LEFT JOIN UserRole ur ON u.userId = ur.userId " +
                    "LEFT JOIN Role r ON ur.roleId = r.roleId " +
                    "WHERE (:username IS NULL OR :username = '' OR u.username = :username)"
    )
    Page<UserDTO> findByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT new com.nusiss.idss.dto.ImageSyncDTO(u.userId, u.profilePictureUrl) FROM User u WHERE u.username = :username and u.profilePictureUrl != '' and u.profilePictureUrl is not null and (u.statusCode != '1' OR u.statusCode IS NULL) ")
    List<ImageSyncDTO> getUserImages(@Param("username") String username);

}
