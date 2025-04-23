package com.nusiss.idss.po;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "Roles")
public class Role extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", length = 36)
    private Integer roleId;

    @Column(name = "role_name", length = 50)
    private String roleName;

    @Column(name = "description", length = 500)
    private String description;
}
