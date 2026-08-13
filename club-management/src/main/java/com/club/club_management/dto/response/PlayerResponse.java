package com.club.club_management.dto.response;

import java.time.LocalDate;

import com.club.club_management.entity.DominantFoot;
import com.club.club_management.entity.Players;

public class PlayerResponse {

    private Integer id;
    private Integer userId;
    private Integer clubId;
    private DominantFoot dominantFoot;
    private String position;
    private LocalDate dateOfBirth;
    private LocalDate joinedOn;
    private String category;
    private Boolean active;

    public PlayerResponse() {
    }

    public PlayerResponse(Players player) {
        this.id = player.getId();

        if (player.getUser() != null) {
            this.userId = player.getUser().getId();
        }

        if (player.getClub() != null) {
            this.clubId = player.getClub().getId();
        }

        this.dominantFoot = player.getDominantFoot();
        this.position = player.getPosition();
        this.dateOfBirth = player.getDateOfBirth();
        this.joinedOn = player.getJoinedOn();
        this.category = player.getCategory();
        this.active = player.getActive();
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getClubId() {
        return clubId;
    }

    public DominantFoot getDominantFoot() {
        return dominantFoot;
    }

    public String getPosition() {
        return position;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getJoinedOn() {
        return joinedOn;
    }

    public String getCategory() {
        return category;
    }

    public Boolean getActive() {
        return active;
    }
}