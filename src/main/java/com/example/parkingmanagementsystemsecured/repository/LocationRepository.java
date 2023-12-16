package com.example.parkingmanagementsystemsecured.repository;

import com.example.parkingmanagementsystemsecured.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    Page<Location> findAllByLocationNameContaining (String name, Pageable pageable);
}
