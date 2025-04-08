package com.nusiss.idss.po;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "User_Device_Relationship")
public class UserDeviceRelationship extends BaseEntity {

    @Id
    @Column(name = "relationship_id")
    private Integer relationshipId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "device_id")
    private Integer deviceId;
}
