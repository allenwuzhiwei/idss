package com.nusiss.idss.po;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "User_Roles")
public class UserRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id", length = 36)
    private Integer userRoleId;

    @Column(name = "user_id", length = 36)
    private Integer userId;

    @Column(name = "role_id", length = 36)
    private Integer roleId;
}
