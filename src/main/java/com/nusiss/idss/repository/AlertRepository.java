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
            value = "SELECT new com.nusiss.idss.dto.AlertDTO(a.alertMessage, a.alertTitle, a.alertDatetime, a.alertId) " +
                    "FROM Alert a " +
                    "LEFT JOIN Device d on a.deviceId = d.deviceId " +
                    "LEFT JOIN User u on u.username = d.createUser  " +
                    "WHERE u.username = :userName"
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
