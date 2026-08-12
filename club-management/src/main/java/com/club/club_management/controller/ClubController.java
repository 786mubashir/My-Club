package com.club.club_management.controller;


import com.club.club_management.entity.Club;
import com.club.club_management.entity.Users;
import com.club.club_management.service.ClubService;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService){
        this.clubService = clubService;
    }
    @PostMapping
    public ResponseEntity<Club> createClub(
            @RequestParam String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String logoUrl,
            Authentication authentication
            ){
        Users manager = (Users) authentication.getPrincipal();

        Club club = clubService.createClub(
                name,city,address,logoUrl,manager
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(club);

    }
    @GetMapping
    public ResponseEntity<Club> getMyClub(Authentication authentication){
        Users manager = (Users) authentication.getPrincipal();

        Club club = clubService.getMyClub(manager);
        return ResponseEntity.ok(club);
    }

    @PutMapping
    public ResponseEntity<Club> updateMyClub(
            @RequestParam String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String logoUrl,
            Authentication authentication
            ){
        Users manager = (Users) authentication.getPrincipal();

        Club club = clubService.UpdateClub(name,city,address,logoUrl,manager);

        return ResponseEntity.ok(club);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteClub(Authentication authentication){
        Users manager = (Users) authentication.getPrincipal();
        clubService.deleteClub(manager);
        return ResponseEntity.noContent().build();
    }

}
