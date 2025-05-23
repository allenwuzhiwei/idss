package com.nusiss.idss.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Permissions")
public class Permission extends BaseEntity {

    @Id
    @Column(name = "permission_id", length = 36)
    private Integer permissionId;

    @Column(name = "permission_name", length = 50)
    private String permissionName;

    @Column(name = "permission_formal_name", length = 50)
    private String permissionFormalName;

    @Column(name = "description", length = 500)
    private String description;
}
