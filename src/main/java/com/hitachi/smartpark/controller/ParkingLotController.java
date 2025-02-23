package com.hitachi.smartpark.controller;

import com.hitachi.smartpark.repository.entity.ParkingLotEntity;
import com.hitachi.smartpark.repository.entity.VehicleEntity;
import com.hitachi.smartpark.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingService parkingService;

    @PostMapping(value = "/parking-lot")
    public ResponseEntity<?> registerParkingLot(@RequestBody ParkingLotEntity parkingLotEntity) {

        return parkingService.registerParkingLot(parkingLotEntity);
    }

    @PostMapping(value = "/parking-lot/{lotId}/checkin")
    public ResponseEntity<?> vehicleCheckIn(@PathVariable Long lotId, @RequestBody VehicleEntity vehicle) {

        return parkingService.vehicleCheckIn(lotId, vehicle.getLicensePlate());
    }

    @PostMapping(value = "/parking-lot/{lotId}/checkout")
    public ResponseEntity<?> vehicleCheckOut(@PathVariable Long lotId, @RequestBody VehicleEntity vehicle) {

        return parkingService.vehicleCheckOut(lotId, vehicle.getLicensePlate());
    }

    @GetMapping(value = "/parking-lot/{lotId}/status")
    public ResponseEntity<?> viewParkingLotAvailability(@PathVariable Long lotId) {

        return parkingService.viewParkingLotAvailability(lotId);
    }

    @GetMapping(value = "/parking-lot/{lotId}/vehicles")
    public ResponseEntity<?> viewParkedVehicles(@PathVariable Long lotId) {

        return parkingService.viewParkedVehicles(lotId);
    }
}
