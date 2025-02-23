package com.hitachi.smartpark.service.impl;

import com.hitachi.smartpark.repository.ParkingLotRepository;
import com.hitachi.smartpark.repository.VehicleRepository;
import com.hitachi.smartpark.repository.entity.ParkingLotEntity;
import com.hitachi.smartpark.repository.entity.VehicleEntity;
import com.hitachi.smartpark.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hitachi.smartpark.utils.ValidationsUtils.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingLotRepository parkingLotRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public ResponseEntity<?> registerParkingLot(ParkingLotEntity parkingLotEntity) {

        ParkingLotEntity parkingLot = parkingLotRepository.findByLocation(parkingLotEntity.getLocation());

        validateParkingExists(parkingLot);

        ParkingLotEntity newParkingLot = parkingLotRepository.save(parkingLotEntity);

        return ResponseEntity.ok(newParkingLot);
    }

    @Override
    public ResponseEntity<?> registerVehicle(VehicleEntity vehicleEntity) {

        vehicleRepository.save(vehicleEntity);

        return ResponseEntity.ok("Vehicle Registered!");
    }

    @Override
    public ResponseEntity<?> vehicleCheckIn(Long lotId, String licensePlate) {

        ParkingLotEntity parkingLot = parkingLotRepository.findById(lotId).orElse(null);
        VehicleEntity vehicle = vehicleRepository.findById(licensePlate).orElse(null);

        validateParkingLotRegistration(parkingLot);
        validateVehicleRegistration(vehicle);
        validateVehicleIsParked(vehicle);
        validateParkingLotOccupancy(parkingLot);

        vehicle.setParkingLotId(lotId);
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() + 1);

        vehicleRepository.save(vehicle);
        parkingLotRepository.save(parkingLot);

        return ResponseEntity.ok("Vehicle checked in successfully!");
    }

    @Override
    public ResponseEntity<?> vehicleCheckOut(Long lotId, String licensePlate) {

        ParkingLotEntity parkingLot = parkingLotRepository.findById(lotId).orElse(null);
        VehicleEntity vehicle = vehicleRepository.findById(licensePlate).orElse(null);

        validateParkingLotRegistration(parkingLot);
        validateVehicleRegistration(vehicle);
        validateVehicleIsNotParked(vehicle);

        vehicle.setParkingLotId(null);
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() - 1);

        vehicleRepository.save(vehicle);
        parkingLotRepository.save(parkingLot);

        return ResponseEntity.ok("Vehicle checked out successfully!");
    }

    @Override
    public ResponseEntity<?> viewParkingLotAvailability(Long lotId) {

        ParkingLotEntity parkingLot = parkingLotRepository.findById(lotId).orElse(null);

        validateParkingLotRegistration(parkingLot);

        return ResponseEntity.ok(parkingLot);
    }

    @Override
    public ResponseEntity<?> viewParkedVehicles(Long lotId) {

        ParkingLotEntity parkingLot = parkingLotRepository.findById(lotId).orElse(null);
        List<VehicleEntity> parkedVehicles = vehicleRepository.findAllByParkingLotId(lotId);

        validateParkingLotRegistration(parkingLot);

        return ResponseEntity.ok(parkedVehicles);
    }
}
