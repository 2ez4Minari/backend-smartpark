package com.hitachi.smartpark.service;

import com.hitachi.smartpark.repository.entity.ParkingLotEntity;
import com.hitachi.smartpark.repository.entity.VehicleEntity;
import org.springframework.http.ResponseEntity;

public interface ParkingService {

    ResponseEntity<?> registerParkingLot(ParkingLotEntity parkingLotEntity);

    ResponseEntity<?> registerVehicle(VehicleEntity vehicleEntity);

    ResponseEntity<?> vehicleCheckIn(Long lotId, String licensePlate);

    ResponseEntity<?> vehicleCheckOut(Long lotId, String licensePlate);

    ResponseEntity<?> viewParkingLotAvailability(Long lotId);

    ResponseEntity<?> viewParkedVehicles(Long lotId);
}
