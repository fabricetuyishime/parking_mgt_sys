package com.example.parkingmanagementsystemsecured.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue
    private UUID id;
    private String locationName;
    private Integer noOfParkingSpots;
    private Integer availableSpots;


}
