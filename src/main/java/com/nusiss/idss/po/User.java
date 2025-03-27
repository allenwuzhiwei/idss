package com.nusiss.idss.po;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    private String userId;
    private String username;
    private String password;
    private String roleId;
    private String email;
    private String phoneNumber;
    private String status;
    private LocalDateTime lastLogin;
    private String profilePictureUrl;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
    private String createUser;
    private String updateUser;
}
