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
    private int id;

    // user_id
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;

    // club_id
    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    // dominant_foot
   public enum DominantFoot {
    LEFT,
    RIGHT,
    BOTH
}

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
}