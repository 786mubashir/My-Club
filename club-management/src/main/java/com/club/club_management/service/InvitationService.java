package com.club.club_management.service;


import com.club.club_management.entity.Invitations;
import com.club.club_management.entity.Users;

public interface InvitationService {
    Invitations createInvitation(String email,Users manager);
    Invitations getInvitationByToken(String token);
}
