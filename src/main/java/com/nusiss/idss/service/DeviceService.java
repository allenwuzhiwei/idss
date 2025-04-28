package com.nusiss.idss.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusiss.idss.config.CustomException;
import com.nusiss.idss.po.Device;
import com.nusiss.idss.po.User;
import com.nusiss.idss.po.UserDeviceRelationship;
import com.nusiss.idss.repository.DeviceRepository;
import com.nusiss.idss.repository.UserDeviceRelationshipRepository;
import com.nusiss.idss.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private UserDeviceRelationshipRepository userDeviceRelationshipRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RedisCrudService redisCrudService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    public Page<Device> getAllDevices(String deviceName, Pageable pageable) {
        return deviceRepository.findAllByDeviceNameContaining(deviceName, pageable);
    }

    public Device updateCameraDeviceWithSerialNumber(String serialNumber, String streamURL, HttpServletRequest request){

        Device device = deviceRepository.findBySerialNumber(serialNumber);
        device.setStreamURL(streamURL);
        User user = jwtUtils.getCurrentUserInfo(request);
        //did it in the BaseEntity
        //device.setUpdateDatetime(LocalDateTime.now());
        device.setUpdateUser(user.getUsername());
        return deviceRepository.save(device);
    }

    public Optional<Device> getDeviceById(Integer id) {
        return deviceRepository.findById(id);
    }

    public Optional<Device> getDeviceByCurrentUser(HttpServletRequest request) {
        User user = jwtUtils.getCurrentUserInfo(request);

        return deviceRepository.findByCreateUserAndDeviceType(user.getUsername(), "Camera");
    }

    @Transactional
    public Device createDevice(Device device, HttpServletRequest request) {
        //save device
        User currentUser = jwtUtils.getCurrentUserInfo(request);
        String username = currentUser.getUsername();
        UserDeviceRelationship userDeviceRelationship = new UserDeviceRelationship();
        device.setCreateUser(username);
        device.setUpdateUser(username);
        Device devicePo = deviceRepository.save(device);
        if(redisCrudService.exists(username)){
           userDeviceRelationship.setUserId(currentUser.getUserId());
           userDeviceRelationship.setDeviceId(devicePo.getDeviceId());
           userDeviceRelationship.setCreateUser(username);
           userDeviceRelationship.setUpdateUser(username);
           // save
           userDeviceRelationshipRepository.save(userDeviceRelationship);
        } else {
            throw new CustomException("Fail to get user info: user didn't login");
        }

        return devicePo;

    }

    public Device updateDevice(Integer id, Device updatedDevice) {
        return deviceRepository.findById(id)
                .map(device -> {
                    if (updatedDevice.getDeviceName() != null && !updatedDevice.getDeviceName().isBlank()) {
                        device.setDeviceName(updatedDevice.getDeviceName());
                    }
                    if (updatedDevice.getDeviceType() != null && !updatedDevice.getDeviceType().isBlank()) {
                        device.setDeviceType(updatedDevice.getDeviceType());
                    }
                    if (updatedDevice.getStatus() != null && !updatedDevice.getStatus().isBlank()) {
                        device.setStatus(updatedDevice.getStatus());
                    }
                    if (updatedDevice.getIpAddress() != null && !updatedDevice.getIpAddress().isBlank()) {
                        device.setIpAddress(updatedDevice.getIpAddress());
                    }
                    if (updatedDevice.getSerialNumber() != null && !updatedDevice.getSerialNumber().isBlank()) {
                        device.setSerialNumber(updatedDevice.getSerialNumber());
                    }
                    if (updatedDevice.getUpdateUser() != null && !updatedDevice.getUpdateUser().isBlank()) {
                        device.setUpdateUser(updatedDevice.getUpdateUser());
                    }

                    device.setUpdateDatetime(java.time.LocalDateTime.now());
                    return deviceRepository.save(device);
                })
                .orElseThrow(() -> new RuntimeException("Device not found"));
    }

    @Transactional
    public void deleteDevice(Integer id) {

        deviceRepository.deleteById(id);
        userDeviceRelationshipRepository.deleteByDeviceId(id);
    }
}
