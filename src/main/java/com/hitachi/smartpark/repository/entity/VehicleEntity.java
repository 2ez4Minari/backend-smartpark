package com.hitachi.smartpark.repository.entity;

import com.hitachi.smartpark.service.model.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "VEHICLE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity {

    @Id
    private String licensePlate;

    private VehicleType type;
    private String ownerName;
    private Long parkingLotId;
}
