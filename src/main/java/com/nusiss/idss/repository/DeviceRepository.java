package com.nusiss.idss.repository;

import com.nusiss.idss.po.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    Page<Device> findAllByDeviceNameContaining(@Param(value = "deviceName") String deviceName, Pageable pageable);

    Device findBySerialNumber(@Param(value = "serialNumber") String serialNumber);

    Optional<Device> findByCreateUserAndDeviceType(@Param(value = "userName") String userName, @Param(value = "deviceType") String deviceType);
}
