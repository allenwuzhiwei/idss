package com.nusiss.idss.service;

import com.nusiss.idss.po.DeviceData;
import com.nusiss.idss.repository.DeviceDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceDataService {

    private final DeviceDataRepository repository;

    public DeviceDataService(DeviceDataRepository repository) {
        this.repository = repository;
    }

    public List<DeviceData> getAll() { return repository.findAll(); }
    public DeviceData getById(String id) { return repository.findById(id).orElse(null); }
    public DeviceData save(DeviceData data) { return repository.save(data); }
    public void delete(String id) { repository.deleteById(id); }
}
