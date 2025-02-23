package com.hitachi.smartpark.service.impl;


import com.hitachi.smartpark.exception.InvalidRequestException;
import com.hitachi.smartpark.repository.ParkingLotRepository;
import com.hitachi.smartpark.repository.VehicleRepository;
import com.hitachi.smartpark.repository.entity.ParkingLotEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ParkingServiceImplTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Test
    public void shouldRegisterParkingLotIfRequestIsValid() {

        ParkingLotEntity request = ParkingLotEntity.builder()
                .location("Makati")
                .capacity(10)
                .build();

        Mockito.when(parkingLotRepository.findByLocation(any())).thenReturn(null);

        var result = parkingService.registerParkingLot(request);

        assertNotNull(result);
        assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(200));
    }

    @Test
    public void shouldThrowInvalidRequestExceptionIfParkingLotExist() {

        ParkingLotEntity request = ParkingLotEntity.builder()
                .location("Makati")
                .capacity(10)
                .build();

        Mockito.when(parkingLotRepository.findByLocation(any())).thenReturn(new ParkingLotEntity());

        InvalidRequestException thrown = assertThrows(
                InvalidRequestException.class,
                () -> parkingService.registerParkingLot(request)
        );

        assertTrue(thrown.getMessage().contains("Parking Lot already exists!"));
    }

}