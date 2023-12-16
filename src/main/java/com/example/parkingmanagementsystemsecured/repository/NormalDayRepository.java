package com.example.parkingmanagementsystemsecured.repository;

import com.example.parkingmanagementsystemsecured.model.Location;
import com.example.parkingmanagementsystemsecured.model.NormalDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NormalDayRepository extends JpaRepository<NormalDay, UUID> {
    Page<NormalDay> findNormalDayByAmountIsNullAndExitTimeIsNull(Pageable pageable);
    Page<NormalDay> findNormalDayByAmountIsNotNullAndExitTimeIsNotNull(Pageable pageable);
    NormalDay findNormalDayByNumberPlateAndExitTimeIsNotNull(String plate);
    NormalDay findNormalDayByNumberPlateAndExitTimeIsNull(String plate);
    Page<NormalDay> findAllByNumberPlateContaining (String name, Pageable pageable);
}
