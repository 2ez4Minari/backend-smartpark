package com.hitachi.smartpark.utils;

import com.hitachi.smartpark.exception.InvalidRequestException;
import com.hitachi.smartpark.repository.entity.ParkingLotEntity;
import com.hitachi.smartpark.repository.entity.VehicleEntity;
import lombok.RequiredArgsConstructor;

import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class ValidationsUtils {

    public static void validateParkingExists(ParkingLotEntity parkingLot) throws InvalidRequestException {

        if (!isEmpty(parkingLot)) {
            throw new InvalidRequestException("Parking Lot already exists!");
        }
    }

    public static void validateParkingLotRegistration(ParkingLotEntity parkingLot) throws InvalidRequestException {

        if (isEmpty(parkingLot)) {
            throw new InvalidRequestException("Parking Lot not registered!");
        }
    }

    public static void validateVehicleRegistration(VehicleEntity vehicle) throws InvalidRequestException {

        if (isEmpty(vehicle)) {
            throw new InvalidRequestException("Vehicle not registered!");
        }
    }

    public static void validateVehicleIsParked(VehicleEntity vehicle) throws InvalidRequestException {

        if (!isEmpty(vehicle.getParkingLotId())) {
            throw new InvalidRequestException("Vehicle is currently parked!");
        }
    }

    public static void validateVehicleIsNotParked(VehicleEntity vehicle) throws InvalidRequestException {

        if (isEmpty(vehicle.getParkingLotId())) {
            throw new InvalidRequestException("Vehicle is not parked!");
        }
    }

    public static void validateParkingLotOccupancy(ParkingLotEntity parkingLot) throws InvalidRequestException {

        if (parkingLot.getCapacity() == parkingLot.getOccupiedSpaces()) {
            throw new InvalidRequestException("Parking Lot is currently full!");
        }
    }
}


