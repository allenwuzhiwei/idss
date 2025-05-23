package com.nusiss.idss.service;


import com.nusiss.idss.dto.AlertDTO;
import com.nusiss.idss.dto.AlertDetailDTO;
import com.nusiss.idss.po.Alert;
import com.nusiss.idss.po.AlertDeviceDataRelationship;
import com.nusiss.idss.po.DeviceData;
import com.nusiss.idss.po.User;
import com.nusiss.idss.repository.AlertDeviceDataRelationshipRepository;
import com.nusiss.idss.repository.AlertRepository;
import com.nusiss.idss.repository.DeviceDataRepository;
import com.nusiss.idss.utils.JwtUtils;
import com.nusiss.idss.vo.AlertVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository repository;

    @Autowired
    private DeviceDataRepository deviceDataRepository;

    @Autowired
    private AlertDeviceDataRelationshipRepository alertDeviceDataRelationshipRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private JwtUtils jwtUtil;

    public List<Alert> getAllAlerts() {
        return repository.findAll();
    }

    /*public Page<AlertDTO> getAlerts(Pageable pageable, HttpServletRequest request) {
        User user = jwtUtil.getCurrentUserInfo(request);
        return repository.fetchAlerts(pageable, user.getUsername());
    }*/

    public Page<AlertDTO> getAlerts(Pageable pageable, HttpServletRequest request) {
        User user = jwtUtil.getCurrentUserInfo(request);
        Page<AlertDTO> alertsPage = repository.fetchAlerts(pageable, user.getUsername());
        alertsPage.getContent().forEach(alert ->
                alert.setAlertDatetime(alert.getAlertDatetime().atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.of("Asia/Singapore")).toLocalDateTime())
        );
        return alertsPage;
    }

    public List<AlertDetailDTO> getAlertById(Integer id, HttpServletRequest request) {

        User user = jwtUtil.getCurrentUserInfo(request);
        List<AlertDetailDTO> alertDetailDTOs = repository.fetchAlertDetailsById(id, user.getUsername());
        for(AlertDetailDTO alertDetailDTO: alertDetailDTOs){
            String mediaUrl = alertDetailDTO.getMediaUrl();
            String presignedUrl = s3Service.generatePresignedUrl(mediaUrl);
            alertDetailDTO.setMediaUrl(presignedUrl);
        }

        return alertDetailDTOs;
    }


    @Transactional
    public Alert createAlert(AlertVO alertVO) {
        Alert alert = new Alert();
        try {
            copyFields(alertVO, alert);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        List<DeviceData> deviceData = alertVO.getDeviceData();
        //save to Alerts table
        repository.save(alert);
        for(DeviceData deviceActualData : deviceData){
            //save device data
            String mediaUrl = deviceActualData.getMediaUrl();
            //extract the format
            if(!StringUtils.isEmpty(mediaUrl) && mediaUrl.contains(".")){
                String[] format = mediaUrl.split("\\.");
                deviceActualData.setFileFormat(format[format.length-1]);
            }

            deviceDataRepository.save(deviceActualData);
            AlertDeviceDataRelationship alertDeviceDataRelationship = new AlertDeviceDataRelationship();
            alertDeviceDataRelationship.setAlertId(alert.getAlertId());
            alertDeviceDataRelationship.setDataId(deviceActualData.getDataId());
            alertDeviceDataRelationshipRepository.save(alertDeviceDataRelationship);
        }

        return repository.save(alert);
    }

    public static void copyFields(Object source, Object target) throws IllegalAccessException {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();

        // Loop through source fields and copy matching fields to target
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().equals(targetField.getName()) &&
                        sourceField.getType().equals(targetField.getType())) {

                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
                    targetField.set(target, sourceField.get(source));
                }
            }
        }
    }

    public Alert updateAlert(Integer id, Alert alert) {
        if (repository.existsById(id)) {
            alert.setAlertId(id);
            return repository.save(alert);
        }
        return null;
    }

    public boolean deleteAlert(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
