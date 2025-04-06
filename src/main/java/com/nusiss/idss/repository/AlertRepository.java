package com.nusiss.idss.repository;

import com.nusiss.idss.dto.AlertDTO;
import com.nusiss.idss.dto.AlertDetailDTO;
import com.nusiss.idss.po.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AlertRepository extends JpaRepository<Alert, Integer> {

    @Query(
            value = "SELECT alert_id AS alertId, alert_message AS alertMessage, alert_title AS alertTitle ," +
                    " alert_datetime AS alertDatetime from Alerts ",
            countQuery = "SELECT COUNT(alert_id) " +
                    "from Alerts ",
            nativeQuery = true
    )
    Page<AlertDTO> fetchAlertDetails(Pageable pageable);

    @Query(value = "SELECT a.alert_id AS alertId,a.alert_type AS alertType, a.alert_title AS alertTitle, a.alert_message AS alertMessage, a.severity_level AS severityLevel, a.acknowledged AS acknowledged, a.acknowledged_by AS acknowledgedBy, a.alert_datetime AS alertDatetime, a.acknowledged_at AS acknowledgedAt, d.data_id AS dataId, d.device_id AS deviceId, d.data_type AS dataType, d.data_value AS dataValue, d.media_url AS mediaUrl, d.file_format AS fileFormat, d.file_size AS fileSize, d.timestamp AS TIMESTAMP, d.processed AS processed FROM Alert_Device_Data_Relationship ad LEFT JOIN Device_Data d ON ad.data_id = d.data_id LEFT JOIN Alerts a ON ad.alert_id = a.alert_id " +
            "WHERE a.alert_id = :id", nativeQuery = true)
    List<AlertDetailDTO> fetchAlertDetailsById(@Param("id") Integer id);
}
