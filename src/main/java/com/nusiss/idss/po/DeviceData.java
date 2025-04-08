package com.nusiss.idss.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/*-- Table for storing data captured by sensors
CREATE TABLE Device_Data (
        data_id VARCHAR(36) PRIMARY KEY, -- Unique identifier for each data record
device_id VARCHAR(36), -- Identifier of the sensor that generated the data
data_type VARCHAR(50), -- Type of data (e.g., image, video, temperature, motion)
data_value TEXT, -- Actual data captured by the sensor (like numeric values)
media_url VARCHAR(255), -- URL where captured images or video files are stored
file_format VARCHAR(10), -- Format of the media file (e.g., jpg, mp4)
file_size INT, -- Size of the media file in bytes
TIMESTAMP DATETIME, -- Time when the data was captured
processed BOOLEAN, -- Boolean indicating whether the data has been processed
create_datetime DATETIME, -- Timestamp when the data record was created
        update_datetime DATETIME, -- Timestamp when the data record was last updated
        create_user VARCHAR(50), -- User who created the data record
update_user VARCHAR(50) -- User who last updated the data record
);*/
@Entity
@Data
@Table(name = "Device_Data")
public class DeviceData extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id")
    private Integer dataId;

    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "data_value")
    private String dataValue;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "file_format")
    private String fileFormat;

    @Column(name = "file_size")
    private int fileSize;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    private boolean processed;
}
