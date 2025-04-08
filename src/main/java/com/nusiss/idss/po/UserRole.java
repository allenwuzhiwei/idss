package com.nusiss.idss.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "User_Roles")
public class UserRole extends BaseEntity {

    @Id
    @Column(name = "user_role_id", length = 36)
    private Integer userRoleId;

    @Column(name = "user_id", length = 36)
    private Integer userId;

    @Column(name = "role_id", length = 36)
    private Integer roleId;
}
