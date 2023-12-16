package com.example.parkingmanagementsystemsecured.service.interfaces;

import com.example.parkingmanagementsystemsecured.model.Location;
import com.example.parkingmanagementsystemsecured.model.NormalDay;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NormalDayService {

    List<NormalDay> findAllNormalDay();
    void saveNormalDay(NormalDay normalDay);
    Optional<NormalDay> getNormalDayById(UUID id);
    void deleteNormalDayById(UUID id);
    List<NormalDay> unpaid();
    NormalDay findNumberPlateToPay(String plate);
    Page<NormalDay> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<NormalDay>search(String name, int pageNo, int pageSize, String sortField, String sortDirection);
    public Page<NormalDay> unpaid(int pageNo, int pageSize, String sortField, String sortDirection);
    public Page<NormalDay> paid(int pageNo, int pageSize, String sortField, String sortDirection);
}
