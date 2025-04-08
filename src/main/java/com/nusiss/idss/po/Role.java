package com.nusiss.idss.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "Roles")
public class Role extends BaseEntity{

    @Id
    @Column(name = "role_id", length = 36)
    private Integer roleId;

    @Column(name = "role_name", length = 50)
    private String roleName;

    @Column(name = "description", length = 500)
    private String description;
}
