package com.club.club_management.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invitations")
public class Invitations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean accepted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and Setters
    public int getId() {
        return id;
    }
    public Club getClub() {
        return club;
    }
    public String getEmail() {
        return email;
    }
    public String getToken() {
        return token;
    }
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    public boolean isAccepted() {
        return accepted;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setClub(Club club) {
        this.club = club;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }   
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}