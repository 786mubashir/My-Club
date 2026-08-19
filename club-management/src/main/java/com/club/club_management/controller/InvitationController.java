package com.club.club_management.controller;

import com.club.club_management.entity.Invitations;
import com.club.club_management.entity.Users;
import com.club.club_management.service.InvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService){
        this.invitationService =invitationService;
    }
//
//    @PostMapping
//    public ResponseEntity<String> createInvitation(
//            @RequestParam String email,
//            Authentication authentication) {
//
//        System.out.println("========== INVITATION ==========");
//        System.out.println("Authentication: " + authentication);
//        System.out.println("Principal: " + authentication.getPrincipal());
//        System.out.println("Authenticated: " + authentication.isAuthenticated());
//
//        return ResponseEntity.ok("Controller reached");
//    }

    @PostMapping
    public ResponseEntity<Invitations> createInvitation(@RequestParam String email, Authentication authentication){
        Users manager = (Users) authentication.getPrincipal();

        Invitations invitations = invitationService.createInvitation(email,manager);
        return ResponseEntity.status(HttpStatus.CREATED).body(invitations);
    }

    @GetMapping("/{token}")
    public ResponseEntity<Invitations> getInvitation(@PathVariable String token){
        Invitations invitations = invitationService.getInvitationByToken(token);

        return ResponseEntity.ok(invitations);
    }
}
