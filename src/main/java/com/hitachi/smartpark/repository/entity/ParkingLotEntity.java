package com.hitachi.smartpark.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARKING_LOT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String location;
    private int capacity;
    private int occupiedSpaces;
}
