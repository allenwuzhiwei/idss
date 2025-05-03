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


public interface AlertRepository extends JpaRepository<Alert, Integer> {

    @Query(
            value = "SELECT a.alert_id AS alertId, a.alert_message AS alertMessage, a.alert_title AS alertTitle ," +
                    " a.alert_datetime AS alertDatetime from Alerts a left join Devices d on a.device_id = d.device_id" +
                    " left join Users u on u.username = d.create_user where u.username = :userName",
            countQuery = "SELECT COUNT(alert_id) " +
                    "from Alerts ",
            nativeQuery = true
    )
    Page<AlertDTO> fetchAlerts(Pageable pageable, @Param("userName") String userName);

    @Query("SELECT new com.nusiss.idss.dto.AlertDetailDTO(" +
            "a.alertId, a.deviceId, a.alertType, a.alertTitle, a.alertMessage, a.severityLevel, " +
            "a.acknowledged, a.acknowledgedBy, a.alertDatetime, a.acknowledgedAt, " +
            "d.dataId, d.dataType, d.dataValue, d.mediaUrl, d.fileFormat, d.fileSize, " +
            "d.timestamp, d.processed) " +
            "FROM Alert a " +
            "JOIN AlertDeviceDataRelationship ad ON a.alertId = ad.alertId " +
            "JOIN DeviceData d ON ad.dataId = d.dataId " +
            "JOIN Device de ON de.deviceId = a.deviceId " +
            "WHERE a.alertId = :id and de.createUser = :userName")
    List<AlertDetailDTO> fetchAlertDetailsById(@Param("id") Integer id, @Param("userName") String userName);
}
