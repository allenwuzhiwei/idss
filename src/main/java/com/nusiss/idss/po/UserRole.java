package com.nusiss.idss.po;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "User_Roles")
public class UserRole {

    @Id
    private String userRoleId;
    private String userId;
    private String roleId;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
    private String createUser;
    private String updateUser;
}
