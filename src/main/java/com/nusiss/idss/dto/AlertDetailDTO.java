package com.nusiss.idss.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public interface AlertDetailDTO {

    Integer getAlertId();

    String getDeviceId();

    String getAlertType();

    String getAlertTitle();

    String getAlertMessage();

    String getSeverityLevel();

    boolean getAcknowledged();

    String getAcknowledgedBy();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getAlertDatetime();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getAcknowledgedAt();

    Integer getDataId();

    String getDataType();

    String getDataValue();

    String getMediaUrl();

    String getFileFormat();

    int getFileSize();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getTimestamp();

    boolean isProcessed(); // Use 'is' for boolean getter methods
}