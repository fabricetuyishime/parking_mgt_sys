package com.example.parkingmanagementsystemsecured.security;

import com.example.parkingmanagementsystemsecured.model.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {
    private String name;
    private String email;
    private String password;
    private List<GrantedAuthority> authorityList;
    private String status;
    public UserInfoDetails(UserInfo userInfo){
        name = userInfo.getName();
        email= userInfo.getEmail();
        password= userInfo.getPassword();
        authorityList= Arrays.stream(userInfo.getRole().split(",")).
                map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        status= userInfo.getStatus();
    }

    public UserInfoDetails() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(status.equals("ACTIVE")){
            return true;
        }else {
            return false;
        }
    }
}
