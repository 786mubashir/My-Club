package com.club.club_management.service;

import com.club.club_management.entity.DominantFoot;
import com.club.club_management.entity.Players;
import com.club.club_management.entity.Users;


import java.time.LocalDate;
import java.util.List;


public interface PlayerService {

    Players createPlayer(
            Users user,
            Users manager,
            DominantFoot dominantFoot,
            String position,
            LocalDate dateOfBrith,
            String category);

    Players updatePlayer(
            Integer playerId,
            DominantFoot dominantFoot,
            String Position,
            LocalDate dateOfBirth,
            String category,
            Boolean active,
            Users manager
    );

    List<Players> getMyClubPlayers(Users manager);

    List<Players> getActivePlayers(Users manager);

    Players getPlayer(Integer playerId, Users manager);

    void deletePlayer(Integer playerId, Users manager);
}


