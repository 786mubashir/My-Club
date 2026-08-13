package com.club.club_management.dto.request;

import java.time.LocalDate;

import com.club.club_management.entity.DominantFoot;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PlayerCreateRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private DominantFoot dominantFoot;

    @Size(max = 30)
    private String position;

    private LocalDate dateOfBirth;

    @Size(max = 10)
    private String category;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public DominantFoot getDominantFoot() {
        return dominantFoot;
    }

    public void setDominantFoot(DominantFoot dominantFoot) {
        this.dominantFoot = dominantFoot;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}