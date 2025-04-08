package com.nusiss.idss.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "Role_Permissions")
public class RolePermission extends BaseEntity{

    @Id
    @Column(name = "role_permission_id", length = 36)
    private Integer rolePermissionId;

    @Column(name = "role_id", length = 36)
    private Integer roleId;

    @Column(name = "permission_id", length = 36)
    private Integer permissionId;
}
