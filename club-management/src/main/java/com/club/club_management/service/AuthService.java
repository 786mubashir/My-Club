package com.club.club_management.service;
import com.club.club_management.dto.request.ManagerRegisterRequest;
import com.club.club_management.dto.request.LoginRequest;
import com.club.club_management.dto.response.LoginResponse;
public interface AuthService {
    
    void registerManager(ManagerRegisterRequest request);
    LoginResponse login(LoginRequest request);
}
