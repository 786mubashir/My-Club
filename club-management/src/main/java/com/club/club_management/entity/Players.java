package com.club.club_management.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Players {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // user_id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;

    // club_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;


    @Enumerated(EnumType.STRING)
    @Column(name = "dominant_foot", nullable = false)
    private DominantFoot dominantFoot;

    // position
    @Column(length = 30)
    private String position;

    // date_of_birth
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    // joined_on
    @Column(name = "joined_on")
    private LocalDate joinedOn;

    // category
    @Column(length = 10)
    private String category;

    // active
    @Column(name = "active")
    private Boolean active = true;

    // created_at
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Getters and Setters

    public Players(){
    }

    public Integer getId(){
        return id;
    }

    public Users getUser(){
        return user;
    }

    public void setUser(Users user){
        this.user = user;
    }

    public Club getClub(){
        return club;
    }

    public void setClub(Club club){
        this.club = club;
    }

    public DominantFoot getDominantFoot(){
        return dominantFoot;
    }

    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public LocalDate getJoinedOn(){
        return joinedOn;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public Boolean getActive(){
        return active;
    }

    public void setActive(Boolean active){
        this.active = active;
    }

    public LocalDateTime getCreateAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public  void setDominantFoot(DominantFoot dominantFoot){
        this.dominantFoot =dominantFoot;
    }

    public void setDateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth =dateOfBirth;
    }
    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }

}