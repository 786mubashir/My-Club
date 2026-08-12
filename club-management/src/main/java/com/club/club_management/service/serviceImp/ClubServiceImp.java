package com.club.club_management.service.serviceImp;

import com.club.club_management.entity.Club;
import com.club.club_management.entity.Users;
import com.club.club_management.repository.ClubRepository;
import com.club.club_management.service.ClubService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClubServiceImp implements ClubService {

    private final ClubRepository clubRepository;

    public ClubServiceImp(ClubRepository clubRepository){
        this.clubRepository = clubRepository;
    }

    @Override
    public Club createClub(String name,String city,String address,String logoUrl, Users manager) {

        if (clubRepository.existsByManager(manager)) {
            throw new RuntimeException(
                    "Manager already has a club"
            );
        }

        Club club = new Club();
        club.setName(name);
        club.setCity(city);
        club.setAddress(address);
        club.setManager(manager);
        club.setLogoUrl(logoUrl);

        return clubRepository.save(club);
    }

    @Override
    public Club getMyClub(Users Manager) {
        return clubRepository.findByManager(Manager)
                .orElseThrow(() ->
                    new RuntimeException("Club not found"));
    }

    @Override
    public Club UpdateClub(String name,
                           String city,
                           String address,
                           String logoUrl,
                           Users manager) {

        Club club = clubRepository.findByManager(manager)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Club not found"
                        )
                );
        club.setName(name);
        club.setCity(city);
        club.setAddress(address);
        club.setLogoUrl(logoUrl);

        return clubRepository.save(club);
    }

    @Override
    public void deleteClub( Users manager) {
        Club club = clubRepository.findByManager(manager)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Club not found"
                        )
                );

        clubRepository.delete(club);
    }
}
