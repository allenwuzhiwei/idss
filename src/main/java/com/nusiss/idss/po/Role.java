package com.nusiss.idss.po;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "Roles")
public class Role {

    @Id
    private String roleId;
    private String roleName;
    private String description;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
    private String createUser;
    private String updateUser;
}
