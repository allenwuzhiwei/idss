package com.nusiss.idss.po;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "Role_Permissions")
public class RolePermission {

    @Id
    private String rolePermissionId;
    private String roleId;
    private String permissionId;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
    private String createUser;
    private String updateUser;
}
