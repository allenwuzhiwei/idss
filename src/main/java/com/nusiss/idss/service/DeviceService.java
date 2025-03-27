package com.nusiss.idss.service;

import com.nusiss.idss.po.Device;
import com.nusiss.idss.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(String id) {
        return deviceRepository.findById(id);
    }

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
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
