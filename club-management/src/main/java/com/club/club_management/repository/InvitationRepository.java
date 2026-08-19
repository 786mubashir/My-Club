package com.club.club_management.repository;

import com.club.club_management.entity.Invitations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitations,Integer> {

    Optional<Invitations> findByToken(String token);
    Boolean existsByToken(String token);
}
