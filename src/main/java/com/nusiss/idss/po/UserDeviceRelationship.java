package com.nusiss.idss.po;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "User_Device_Relationship")
public class UserDeviceRelationship {

    @Id
    private String relationshipId;
    private String userId;
    private String deviceId;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
    private String createUser;
    private String updateUser;
}
