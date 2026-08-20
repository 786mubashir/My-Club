package com.club.club_management.dto.request;

import com.club.club_management.entity.DominantFoot;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class PlayerRegisterRequest {


    @NotBlank
    private String token;


    @Email
    @NotBlank
    private String email;



    @NotBlank
    @Size(min = 6)
    private String password;



    @NotNull
    private DominantFoot dominantFoot;


    private String position;

    private LocalDate dateOfBirth;

    private String category;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
