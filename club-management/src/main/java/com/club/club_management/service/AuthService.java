package com.club.club_management.service;
import com.club.club_management.dto.request.ManagerRegisterRequest;
import com.club.club_management.dto.request.LoginRequest;
import com.club.club_management.dto.request.PlayerRegisterRequest;
import com.club.club_management.dto.response.LoginResponse;
public interface AuthService {
    
    void registerManager(ManagerRegisterRequest request);
    void registerPlayer(PlayerRegisterRequest request);
    LoginResponse login(LoginRequest request);
}
