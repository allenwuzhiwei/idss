package com.nusiss.idss.po;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Devices")
@Data
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "status")
    private String status;

    @Column(name = "ip_address")
    private String ipAddress;


}
