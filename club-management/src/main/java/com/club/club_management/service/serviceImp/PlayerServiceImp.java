package com.club.club_management.service.serviceImp;

import com.club.club_management.entity.Club;
import com.club.club_management.entity.DominantFoot;
import com.club.club_management.entity.Players;
import com.club.club_management.entity.Users;
import com.club.club_management.repository.ClubRepository;
import com.club.club_management.repository.PlayerRepository;
import com.club.club_management.service.PlayerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlayerServiceImp implements PlayerService {

    private PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    PlayerServiceImp(PlayerRepository playerRepository, ClubRepository clubRepository){
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
    }


    @Override
    public Players createPlayer(Users user, Users manager, DominantFoot dominantFoot, String position, LocalDate dateOfBirth, String category) {
        if(playerRepository.existsByUser(user)){
             throw new RuntimeException("User alreay has a player profile");
        }

        Club club = getManagerClub(manager);

        Players player = new Players();

        player.setUser(user);
        player.setClub(club);
        player.setDominantFoot(dominantFoot);
        player.setPosition(position);
        player.setDateOfBirth(dateOfBirth);
        player.setCategory(category);
        player.setActive(true);
        return playerRepository.save(player);
    }

    @Override
    public Players updatePlayer(Integer playerId, DominantFoot dominantFoot, String position, LocalDate dateOfBirth, String category, Boolean active, Users manager) {


        Club club = getManagerClub(manager);

        Players player = playerRepository
                .findByIdAndClub(playerId, club)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Player not found"
                        )
                );

        player.setPosition(position);
        player.setDominantFoot(dominantFoot);
        player.setDateOfBirth(dateOfBirth);
        player.setCategory(category);
        player.setActive(true);

        return playerRepository.save(player);
    }

    @Override
    public List<Players> getMyClubPlayers(Users manager) {
        Club club = getManagerClub(manager);

        return playerRepository.findByClub(club);
    }

    @Override
    public List<Players> getActivePlayers(Users manager) {
        Club club = getManagerClub(manager);

        return playerRepository.findByClubAndActiveTrue(club);
    }

    @Override
    public Players getPlayer(Integer playerId, Users manager) {
        Club club = getManagerClub(manager);

        return playerRepository
                .findByIdAndClub(playerId, club)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Player not found"
                        )
                );
    }

    @Override
    public void deletePlayer(Integer playerId, Users manager) {
        Club club = getManagerClub(manager);

        Players player = playerRepository
                .findByIdAndClub(playerId, club)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Player not found"
                        )
                );

        playerRepository.delete(player);
    }

    private Club getManagerClub(Users manager){
        return clubRepository.findByManager(manager)
                .orElseThrow(()->
                        new RuntimeException(
                                "message does not have a club"
                        ));
    }
}
