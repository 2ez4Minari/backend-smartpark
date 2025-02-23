package com.hitachi.smartpark.repository.entity;

import com.hitachi.smartpark.service.model.VehicleType;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "VEHICLE")
@Data
public class VehicleEntity {

    @Id
    private String licensePlate;

    private VehicleType type;
    private String ownerName;
    private Long parkingLotId;
}
