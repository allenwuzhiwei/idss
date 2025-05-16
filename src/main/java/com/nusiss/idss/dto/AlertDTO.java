package com.nusiss.idss.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class AlertDTO {

    private String alertMessage;
    private String alertTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Singapore")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime alertDatetime;

    private Integer alertId;

    // No-arg constructor for frameworks
    public AlertDTO() {
    }

    // Constructor that matches the query result
    public AlertDTO(String alertMessage, String alertTitle, LocalDateTime alertDatetime, Integer alertId) {
        this.alertMessage = alertMessage;
        this.alertTitle = alertTitle;
        this.alertDatetime = alertDatetime;
        this.alertId = alertId;
    }
}
