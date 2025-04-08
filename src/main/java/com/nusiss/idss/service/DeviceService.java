package com.nusiss.idss.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusiss.idss.config.CustomException;
import com.nusiss.idss.po.Device;
import com.nusiss.idss.po.User;
import com.nusiss.idss.po.UserDeviceRelationship;
import com.nusiss.idss.repository.DeviceRepository;
import com.nusiss.idss.repository.UserDeviceRelationshipRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ObjectMapper objectMapper;

    @Autowired

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(String id) {
        return deviceRepository.findById(id);
    }

    @Transactional
    public Device createDevice(Device device) {
        //save device

        UserDeviceRelationship userDeviceRelationship = new UserDeviceRelationship();

        Device devicePo = deviceRepository.save(device);
        if(redisCrudService.exists("")){
            String userJson = redisCrudService.get("");
            try {
               User user = objectMapper.readValue(userJson, User.class);
               userDeviceRelationship.setUserId(user.getUserId());
               userDeviceRelationship.setDeviceId(devicePo.getDeviceId());
               userDeviceRelationshipRepository.save(userDeviceRelationship);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new CustomException("Fail to get user info: user didn't login");
        }

        return devicePo;

    }

    public Device updateDevice(String id, Device updatedDevice) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setDeviceName(updatedDevice.getDeviceName());
                    device.setDeviceType(updatedDevice.getDeviceType());
                    device.setStatus(updatedDevice.getStatus());
                    device.setIpAddress(updatedDevice.getIpAddress());
                    device.setUpdateUser(updatedDevice.getUpdateUser());
                    device.setUpdateDatetime(java.time.LocalDateTime.now());
                    return deviceRepository.save(device);
                })
                .orElseThrow(() -> new RuntimeException("Device not found"));
    }

    public void deleteDevice(String id) {
        deviceRepository.deleteById(id);
    }
}
