package com.hitachi.smartpark.repository;

import com.hitachi.smartpark.repository.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository <VehicleEntity, String> {

    List<VehicleEntity> findAllByParkingLotId(Long lotId);
}
