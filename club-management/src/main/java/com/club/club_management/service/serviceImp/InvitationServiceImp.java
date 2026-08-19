package com.club.club_management.service.serviceImp;

import com.club.club_management.entity.Club;
import com.club.club_management.entity.Invitations;
import com.club.club_management.entity.Users;
import com.club.club_management.repository.ClubRepository;
import com.club.club_management.repository.InvitationRepository;
import com.club.club_management.service.InvitationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InvitationServiceImp implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final ClubRepository clubRepository;

    public InvitationServiceImp(InvitationRepository invitationRepository, ClubRepository clubRepository){
        this.invitationRepository = invitationRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public Invitations createInvitation(String email, Users manager){

        Club club = clubRepository.findByManager(manager).orElseThrow(()->
                new RuntimeException(
                        "Manager does not have a club"
                ));
        String token = UUID.randomUUID().toString();

        Invitations invitations = new Invitations();

        invitations.setClub(club);
        invitations.setEmail(email);
        invitations.setToken(token);

        invitations.setExpiresAt(
                LocalDateTime.now().plusDays(7)
        );
        invitations.setAccepted(false);


        return invitationRepository.save(invitations);
    }

    @Override
    public Invitations getInvitationByToken(String token){

        Invitations invitations = invitationRepository.findByToken(token).orElseThrow(
                ()->new RuntimeException(
                        new RuntimeException("Invalid invitation")
                )
        );

        if(invitations.getAccepted()){
            throw new RuntimeException("Invitation has already been accepted");
        }

        if (invitations.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Invitation has expired"
            );
        }

        return invitations;
    }
}
