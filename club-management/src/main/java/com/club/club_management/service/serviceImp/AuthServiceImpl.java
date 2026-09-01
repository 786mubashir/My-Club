package com.club.club_management.service.serviceImp;

import com.club.club_management.dto.request.PlayerRegisterRequest;
import com.club.club_management.entity.Invitations;
import com.club.club_management.entity.Players;
import org.apache.catalina.User;
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
import com.club.club_management.repository.InvitationRepository;
import com.club.club_management.repository.PlayerRepository;

import java.time.LocalDateTime;


@Service
public class AuthServiceImpl implements AuthService {

    private final InvitationRepository invitationRepository;
    private final PlayerRepository playerRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService ;


    public AuthServiceImpl(
            UsersRepository usersRepository,
            InvitationRepository invitationRepository,
            PlayerRepository playerRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            JwtService jwtService) {

        this.usersRepository = usersRepository;
        this.invitationRepository = invitationRepository;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository =roleRepository;
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
    public void registerPlayer(PlayerRegisterRequest request) {
        Invitations invitations = invitationRepository.findByToken(request.getToken())
                .orElseThrow(()->
                        new RuntimeException("Invalid invitation"));

        if(invitations.getAccepted()){
            throw new RuntimeException("Invitation already used");
        }
        if(invitations.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Invitation expire");
        }
        if(!invitations.getEmail().equals(request.getEmail())){
            throw new RuntimeException("Email does not match invitation");
        }
        if(usersRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered");
        }
        Role playerRole = roleRepository.findByName("PLAYER")
                .orElseThrow(() -> new IllegalArgumentException("Manager role not found"));

        Users user = new Users();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(playerRole);
        Users savedUser = usersRepository.save(user);

        Players player = new Players();

        player.setUser(savedUser);
        player.setClub(invitations.getClub());
        player.setDominantFoot(request.getDominantFoot());
        player.setPosition(request.getPosition());
        player.setDateOfBirth(request.getDateOfBirth());
        player.setCategory(request.getCategory());
        player.setActive(true);

        playerRepository.save(player);

        invitations.setAccepted(true);

        invitationRepository.save(invitations);

    }

    @Override
    public LoginResponse login(LoginRequest request){
        Users currentUser = usersRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        System.out.println("EMAIL: " + request.getEmail());
        System.out.println("USER FOUND: " + currentUser.getEmail());
        System.out.println("PASSWORD HASH EXISTS: " + (currentUser.getPassword() != null));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                currentUser.getPassword()
        );

        System.out.println("PASSWORD MATCH: " + matches);

        if (!matches) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        String token = jwtService.generateToken(currentUser.getEmail());

    return new LoginResponse(token);

    }

//    @Override
//    public LoginResponse login(LoginRequest request) {
//
//        System.out.println("LOGIN EMAIL: " + request.getEmail());
//
//        Users currentUser = usersRepository.findByEmail(request.getEmail())
//                .orElseThrow(() ->
//                        new IllegalArgumentException("Invalid email or password"));
//
//        System.out.println("USER FOUND: " + currentUser.getEmail());
//
//        System.out.println(
//                "PASSWORD MATCH: " +
//                        passwordEncoder.matches(
//                                request.getPassword(),
//                                currentUser.getPassword()
//                        )
//        );
//
//        if (!passwordEncoder.matches(
//                request.getPassword(),
//                currentUser.getPassword())) {
//
//            throw new IllegalArgumentException(
//                    "Invalid email or password"
//            );
//        }
//
//        String token = jwtService.generateToken(
//                currentUser.getEmail()
//        );
//
//        System.out.println("JWT GENERATED");
//
//        return new LoginResponse(token);
//    }
}
