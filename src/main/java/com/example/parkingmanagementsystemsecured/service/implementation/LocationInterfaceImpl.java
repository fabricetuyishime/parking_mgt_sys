package com.example.parkingmanagementsystemsecured.service.implementation;

import com.example.parkingmanagementsystemsecured.model.Location;
import com.example.parkingmanagementsystemsecured.repository.LocationRepository;
import com.example.parkingmanagementsystemsecured.service.interfaces.LocationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class LocationInterfaceImpl implements LocationInterface {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }

    @Override
    public void saveLocation(Location location) {
        this.locationRepository.save(location);

    }

    @Override
    public Location getLocationById(UUID id) {
        Optional<Location> optional = locationRepository.findById(id);
        Location location = null;
        if (optional.isPresent()) {
            location = optional.get();
        } else {
            throw new RuntimeException("Location not found for id" + id);
        }
        return location;
    }

    @Override
    public void deleteLocationById(UUID id) {
        this.locationRepository.deleteById(id);

    }

    @Override
    public Page<Location> search(String name, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.locationRepository.findAllByLocationNameContaining(name,pageable);
    }

    public Page<Location> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.locationRepository.findAll(pageable);
    }
}
