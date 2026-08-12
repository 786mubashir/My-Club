package com.club.club_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.club.club_management.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import com.club.club_management.dto.request.LoginRequest;
import com.club.club_management.dto.request.ManagerRegisterRequest;
import org.springframework.http.HttpStatus;
// import com.club.club_management.security.JwtService;
import com.club.club_management.dto.response.LoginResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    // private final JwtService jwtService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/register/manager")
    public ResponseEntity<String> registerManagerRequest(@Valid @RequestBody ManagerRegisterRequest request) {
        
        authService.registerManager(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Manager registered successfully");
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);
        
        return ResponseEntity.ok(response);
}

    @RestController
    @RequestMapping("/api/test")
    public class TestController {

        @GetMapping
        public ResponseEntity<String> test() {
            return ResponseEntity.ok("JWT authentication is working");
        }
    }
}

