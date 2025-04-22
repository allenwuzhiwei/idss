package com.nusiss.idss.po;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "User_Device_Relationship")
public class UserDeviceRelationship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Integer relationshipId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "device_id")
    private Integer deviceId;
}
