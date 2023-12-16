package com.example.parkingmanagementsystemsecured.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer timeLimit;
    private BigDecimal amountPerTime;
    public BigDecimal prices(LocalDateTime entry, LocalDateTime exit) {
         BigDecimal amount= BigDecimal.valueOf(200);
        Duration duration= Duration.between(entry,exit);
        float differenceIn = duration.toMinutes();
        float actual = differenceIn/60;
        System.out.println(actual);
        if(actual<=1){
            return amount= BigDecimal.valueOf(200);
        }
        else if(actual<=2){
            return amount= BigDecimal.valueOf(300);
        }
        else if(actual<=3){
            return amount= BigDecimal.valueOf(500);
        }
        else if(actual<=4){
            return amount= BigDecimal.valueOf(1000);
        }
        else if(actual<=5){
            return amount= BigDecimal.valueOf(200);
        }
        else{
            float extendedTime=Math.round(actual)-5;
           return amount= BigDecimal.valueOf((2000*extendedTime)+5000);
        }
    }
}
