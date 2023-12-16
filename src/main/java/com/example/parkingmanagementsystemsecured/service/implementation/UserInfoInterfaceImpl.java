package com.example.parkingmanagementsystemsecured.service.implementation;

import com.example.parkingmanagementsystemsecured.model.UserInfo;
import com.example.parkingmanagementsystemsecured.repository.UserInfoRepository;
import com.example.parkingmanagementsystemsecured.service.interfaces.UserInfoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserInfoInterfaceImpl implements UserInfoInterface {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoRepository repository;
    @Override
    public void saveUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
    }

    @Override
    public Optional<UserInfo> getEmail(String email) {
        return repository.findByEmail(email);
    }
    @Override
    public List<UserInfo> getAllUserInfo() {
        return repository.findAll();
    }

 

    @Override
    public UserInfo getUserInfoById(UUID id) {
        Optional<UserInfo> optional = repository.findById(id);
        UserInfo location = null;
        if (optional.isPresent()) {
            location = optional.get();
        } else {
            throw new RuntimeException("UserInfo not found for id" + id);
        }
        return location;
    }

    @Override
    public void deleteUserInfoById(UUID id) {
        this.repository.deleteById(id);

    }

    @Override
    public Page<UserInfo> search(String name, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.repository.findAllByEmailContaining(name,pageable);
    }

    @Override
    public Page<UserInfo> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.repository.findAll(pageable);
    }
}
