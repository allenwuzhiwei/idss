package com.nusiss.idss.repository;

import com.nusiss.idss.po.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
