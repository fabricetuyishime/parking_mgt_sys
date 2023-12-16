package com.example.parkingmanagementsystemsecured.service.implementation;

import com.example.parkingmanagementsystemsecured.model.NormalDay;
import com.example.parkingmanagementsystemsecured.repository.NormalDayRepository;
import com.example.parkingmanagementsystemsecured.service.interfaces.NormalDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NormalDayInterfaceImp implements NormalDayService {

    @Autowired
    private NormalDayRepository repository;
    @Override
    public List<NormalDay> findAllNormalDay() {
        return repository.findAll();
    }

    @Override
    public void saveNormalDay(NormalDay normalDay) {
        this.repository.save(normalDay);
    }

    @Override
    public Optional<NormalDay> getNormalDayById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public void deleteNormalDayById(UUID id) {
        this.repository.deleteById(id);
    }

    @Override
    public List<NormalDay> unpaid() {
        return null;
    }

    @Override
    public Page<NormalDay> unpaid(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.repository.findNormalDayByAmountIsNullAndExitTimeIsNull(pageable);
    }

    @Override
    public Page<NormalDay> paid(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.repository.findNormalDayByAmountIsNotNullAndExitTimeIsNotNull(pageable);
    }

    @Override
    public NormalDay findNumberPlateToPay(String plate) {
        return repository.findNormalDayByNumberPlateAndExitTimeIsNull(plate);
    }

    @Override
    public Page<NormalDay> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<NormalDay> search(String name, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.repository.findAllByNumberPlateContaining(name,pageable);
    }
}
