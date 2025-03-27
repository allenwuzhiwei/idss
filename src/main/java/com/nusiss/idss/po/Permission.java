package com.nusiss.idss.po;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Permissions")
public class Permission {

    @Id
    private String permissionId;
    private String permissionName;
    private String description;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
    private String createUser;
    private String updateUser;
}
