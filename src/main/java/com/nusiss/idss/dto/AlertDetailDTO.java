package com.nusiss.idss.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class AlertDetailDTO {

    private Integer alertId;
    private String deviceId;
    private String alertType;
    private String alertTitle;
    private String alertMessage;
    private String severityLevel;
    private boolean acknowledged;
    private String acknowledgedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime alertDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acknowledgedAt;

    private Integer dataId;
    private String dataType;
    private String dataValue;
    private String mediaUrl;
    private String fileFormat;
    private int fileSize;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private boolean processed;

    public AlertDetailDTO(Integer alertId, String deviceId, String alertType, String alertTitle,
                          String alertMessage, String severityLevel, boolean acknowledged, String acknowledgedBy,
                          LocalDateTime alertDatetime, LocalDateTime acknowledgedAt, Integer dataId, String dataType,
                          String dataValue, String mediaUrl, String fileFormat, int fileSize,
                          LocalDateTime timestamp, boolean processed) {
        this.alertId = alertId;
        this.deviceId = deviceId;
        this.alertType = alertType;
        this.alertTitle = alertTitle;
        this.alertMessage = alertMessage;
        this.severityLevel = severityLevel;
        this.acknowledged = acknowledged;
        this.acknowledgedBy = acknowledgedBy;
        this.alertDatetime = alertDatetime;
        this.acknowledgedAt = acknowledgedAt;
        this.dataId = dataId;
        this.dataType = dataType;
        this.dataValue = dataValue;
        this.mediaUrl = mediaUrl;
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
        this.timestamp = timestamp;
        this.processed = processed;
    }



}
