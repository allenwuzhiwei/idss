package com.nusiss.idss.po;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Alert_Device_Data_Relationship")
@Data
public class AlertDeviceDataRelationship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Integer relationshipId;

    @Column(name = "alert_id")
    private Integer alertId; // Reference to the Alert entity

    @Column(name = "data_id")
    private Integer dataId; // Reference to the DeviceData entity

}
