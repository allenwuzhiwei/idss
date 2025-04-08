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

    @Query("SELECT new com.nusiss.idss.dto.AlertDetailDTO(" +
            "a.alertId, a.deviceId, a.alertType, a.alertTitle, a.alertMessage, a.severityLevel, " +
            "a.acknowledged, a.acknowledgedBy, a.alertDatetime, a.acknowledgedAt, " +
            "d.dataId, d.dataType, d.dataValue, d.mediaUrl, d.fileFormat, d.fileSize, " +
            "d.timestamp, d.processed) " +
            "FROM Alert a " +
            "JOIN AlertDeviceDataRelationship ad ON a.alertId = ad.alertId " +
            "JOIN DeviceData d ON ad.dataId = d.dataId " +
            "WHERE a.alertId = :id")
    List<AlertDetailDTO> fetchAlertDetailsById(@Param("id") Integer id);
}
