package com.example.parkingmanagementsystemsecured.service.interfaces;

import com.example.parkingmanagementsystemsecured.model.Location;
import com.example.parkingmanagementsystemsecured.model.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserInfoInterface {
    void saveUser(UserInfo userInfo);
    Optional<UserInfo> getEmail(String email);
    List<UserInfo> getAllUserInfo();
    UserInfo getUserInfoById(UUID id);
    void deleteUserInfoById(UUID id);

    Page<UserInfo>search(String name, int pageNo, int pageSize, String sortField, String sortDirection);
    Page<UserInfo> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
