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
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Integer id) {
        return deviceRepository.findById(id);
    }

    @Transactional
    public Device createDevice(Device device, HttpServletRequest request) {
        //save device

        String username = jwtUtils.getCurrentUsername(request);
        UserDeviceRelationship userDeviceRelationship = new UserDeviceRelationship();

        Device devicePo = deviceRepository.save(device);
        if(redisCrudService.exists(username)){
            String userJson = redisCrudService.get(username);
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

    public Device updateDevice(Integer id, Device updatedDevice) {
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

    @Transactional
    public void deleteDevice(Integer id) {

        deviceRepository.deleteById(id);
        userDeviceRelationshipRepository.deleteByDeviceId(id);
    }
}
