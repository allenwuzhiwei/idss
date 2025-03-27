package com.nusiss.idss.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Alerts")
public class Alert extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Integer alertId;

    @Column(name = "device_id")  // Explicit column name mapping
    private String deviceId;

    @Column(name = "alert_type")  // Explicit column name mapping
    private String alertType;

    @Column(name = "alert_title")  // Explicit column name mapping
    private String alertTitle;

    @Column(name = "alert_message")  // Explicit column name mapping
    private String alertMessage;

    @Column(name = "severity_level")  // Explicit column name mapping
    private String severityLevel;

    @Column(name = "acknowledged")  // Explicit column name mapping
    private boolean acknowledged;

    @Column(name = "acknowledged_by")  // Explicit column name mapping
    private String acknowledgedBy;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "alert_datetime")
    private LocalDateTime alertDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "acknowledged_at")
    private LocalDateTime acknowledgedAt;

}
