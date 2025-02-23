package com.hitachi.smartpark.repository;

import com.hitachi.smartpark.repository.entity.ParkingLotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository <ParkingLotEntity, Long> {

    ParkingLotEntity findByLocation(String location);
}
