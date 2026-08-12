package com.club.club_management.service;


import com.club.club_management.entity.Users;
import com.club.club_management.entity.Club;

import java.util.List;

public interface ClubService {

    Club createClub(String name, String city,String address ,String logoUrl, Users manager);
    Club getMyClub(Users Manager);


    Club UpdateClub(String name,
                    String city,
                    String address,
                    String logoUrl,
                    Users manager);

    void deleteClub(Users manager);
}
