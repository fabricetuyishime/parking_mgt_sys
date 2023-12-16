package com.example.parkingmanagementsystemsecured.service.interfaces;

import com.example.parkingmanagementsystemsecured.model.Location;
import com.example.parkingmanagementsystemsecured.model.NormalDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface LocationInterface {
    List<Location> getAllLocation();
    void saveLocation(Location type);
    Location getLocationById(UUID id);
    void deleteLocationById(UUID id);
    Page<Location>search(String name, int pageNo, int pageSize, String sortField, String sortDirection);
    Page<Location> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
