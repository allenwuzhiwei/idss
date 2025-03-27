package com.nusiss.idss.repository;

import com.nusiss.idss.po.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
}
