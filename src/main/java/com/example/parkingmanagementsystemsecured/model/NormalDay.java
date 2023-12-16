package com.example.parkingmanagementsystemsecured.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NormalDay {
    @Id
    @GeneratedValue
    private UUID id;
    private String numberPlate;
    @CreationTimestamp
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private BigDecimal amount;
    @ManyToOne
    private Location location;
}
