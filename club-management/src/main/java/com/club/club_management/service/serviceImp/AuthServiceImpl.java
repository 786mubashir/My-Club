package com.club.club_management.service.serviceImp;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.club.club_management.dto.request.ManagerRegisterRequest;
import com.club.club_management.repository.RoleRepository;
import com.club.club_management.repository.UsersRepository;
import com.club.club_management.service.AuthService;
import com.club.club_management.entity.Role;
import com.club.club_management.entity.Users;
import com.club.club_management.dto.request.LoginRequest;
import com.club.club_management.dto.response.LoginResponse;
import com.club.club_management.security.JwtService;



@Service
public class AuthServiceImpl implements AuthService {
    
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService ;
    
    public AuthServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }
    @Override
    public void registerManager(ManagerRegisterRequest request) {
        // Implementation for registering a manager
        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        Role managerRole = roleRepository.findByName("MANAGER")
                .orElseThrow(() -> new IllegalArgumentException("Manager role not found"));
    
        Users user = new Users();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(managerRole);
        user.setIsActive(true);
        
        usersRepository.save(user);
    
    
    }


    @Override
    public LoginResponse login(LoginRequest request){
        Users currentUser = usersRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), currentUser.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        String token = jwtService.generateToken(currentUser.getEmail());

    return new LoginResponse(token);

    }

}
