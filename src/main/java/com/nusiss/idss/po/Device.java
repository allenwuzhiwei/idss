package com.nusiss.idss.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Devices")
@Data
public class Device {
    @Id
    private String deviceId;

    private String deviceName;
    private String deviceType;
    private String status;
    private String ipAddress;
    private String createUser;
    private String updateUser;

    @Column(name = "create_datetime")
    private java.time.LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    private java.time.LocalDateTime updateDatetime;
}
