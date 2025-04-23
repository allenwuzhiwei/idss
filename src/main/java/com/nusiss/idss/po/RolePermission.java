package com.nusiss.idss.po;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "Role_Permissions")
public class RolePermission extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_permission_id", length = 36)
    private Integer rolePermissionId;

    @Column(name = "role_id", length = 36)
    private Integer roleId;

    @Column(name = "permission_id", length = 36)
    private Integer permissionId;
}
