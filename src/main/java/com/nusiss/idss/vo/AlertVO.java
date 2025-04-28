package com.nusiss.idss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nusiss.idss.po.BaseEntity;
import com.nusiss.idss.po.DeviceData;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AlertVO extends BaseEntity {

    private List<DeviceData> deviceData;

    private Integer alertId;

    private Integer alertUserId;

    private String deviceId;

    private String alertType;

    private String alertMessage;

    private String alertTitle;

    private String severityLevel;

    private boolean acknowledged;

    private String acknowledgedBy;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime alertDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acknowledgedAt;


}
