package com.club.club_management.repository;

import com.club.club_management.entity.Club;
import com.club.club_management.entity.Players;
import com.club.club_management.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Players, Integer> {

    Optional<Players> findByUser(Users user);

    List<Players> findByClub(Club club);

    List<Players> findByClubAndActiveTrue(Club club);

    Optional<Players> findByIdAndClub(Integer id, Club club);

    boolean existsByUser(Users user);
}
