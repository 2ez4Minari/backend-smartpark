package com.hitachi.smartpark.controller;

import com.hitachi.smartpark.repository.entity.VehicleEntity;
import com.hitachi.smartpark.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VehicleController {

    private final ParkingService parkingService;

    @PostMapping(value = "/vehicle")
    public ResponseEntity<?> registerVehicle(@RequestBody VehicleEntity vehicleEntity) {

        return parkingService.registerVehicle(vehicleEntity);
    }
}
