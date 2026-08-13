package com.club.club_management.controller;

import java.time.LocalDate;
import java.util.List;

import com.club.club_management.dto.response.PlayerResponse;
import com.club.club_management.entity.Players;
import com.club.club_management.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.club.club_management.dto.request.PlayerCreateRequest;
import com.club.club_management.repository.UsersRepository;
import com.club.club_management.service.PlayerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;
    private final UsersRepository usersRepository;

    public PlayerController(
            PlayerService playerService,
            UsersRepository usersRepository) {

        this.playerService = playerService;
        this.usersRepository = usersRepository;
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(
            @Valid @RequestBody PlayerCreateRequest request,
            Authentication authentication) {

        Users manager = (Users) authentication.getPrincipal();

        Users playerUser = usersRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Players player = playerService.createPlayer(
                playerUser,
                manager,
                request.getDominantFoot(),
                request.getPosition(),
                request.getDateOfBirth(),
                request.getCategory()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new PlayerResponse(player));
    }

    @GetMapping
    public ResponseEntity<List<Players>> getMyClubPlayers(
            Authentication authentication) {

        Users manager = (Users) authentication.getPrincipal();

        return ResponseEntity.ok(
                playerService.getMyClubPlayers(manager)
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<PlayerResponse>> getActivePlayers(
            Authentication authentication) {

        Users manager = (Users) authentication.getPrincipal();

        List<PlayerResponse> players =
                playerService.getMyClubPlayers(manager)
                        .stream()
                        .map(PlayerResponse::new)
                        .toList();

        return ResponseEntity.ok(players);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerResponse> getPlayer(
            @PathVariable Integer playerId,
            Authentication authentication) {

        Users manager = (Users) authentication.getPrincipal();
        Players player = playerService.getPlayer(playerId, manager);

        return ResponseEntity.ok(new PlayerResponse(player));
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<PlayerResponse> updatePlayer(
            @PathVariable Integer playerId,
            @Valid @RequestBody PlayerCreateRequest request,
            Authentication authentication) {

        Users manager = (Users) authentication.getPrincipal();

        Players player = playerService.updatePlayer(
                playerId,
                request.getDominantFoot(),
                request.getPosition(),
                request.getDateOfBirth(),
                request.getCategory(),
                true,
                manager
        );

        return ResponseEntity.ok(new PlayerResponse(player));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer(
            @PathVariable Integer playerId,
            Authentication authentication) {

        Users manager = (Users) authentication.getPrincipal();

        playerService.deletePlayer(playerId, manager);

        return ResponseEntity.noContent().build();
    }
}