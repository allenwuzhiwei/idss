package com.nusiss.idss.po;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "LOGS")
public class Log {

    @Id
    private String logId;
    private String actionType;
    private String description;
    private String userId;
    private String deviceId;
    private String alertId;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
    private String createUser;
    private String updateUser;
}
