package com.nusiss.idss.repository;

import com.nusiss.idss.dto.ImageSyncDTO;
import com.nusiss.idss.po.ImageSync;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ImageSyncRepository extends JpaRepository<ImageSync, Integer> {

    @Query(value = "SELECT create_datetime AS createDatetime FROM Image_Sync WHERE update_user = :userName ORDER BY create_datetime DESC LIMIT 1", nativeQuery = true)
    LocalDateTime getLastSyncTime(@Param(value = "userName") String userName);

}
