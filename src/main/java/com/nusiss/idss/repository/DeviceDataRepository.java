package com.nusiss.idss.repository;

import com.nusiss.idss.po.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceDataRepository extends JpaRepository<DeviceData, String> {
}
