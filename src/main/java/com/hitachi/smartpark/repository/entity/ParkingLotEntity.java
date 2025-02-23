package com.hitachi.smartpark.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PARKING_LOT")
@Data
public class ParkingLotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String location;
    private int capacity;
    private int occupiedSpaces;
}
