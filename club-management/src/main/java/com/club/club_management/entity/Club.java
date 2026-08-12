package com.club.club_management.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // manager_id
    @OneToOne
    @JoinColumn(name = "manager_id", nullable = false, unique = true)
    private Users manager;

    @NotBlank(message = "Club name is required")
    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 100)
    private String city;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "isactive", nullable = false)
    private boolean isActive = true;

    // Constructors

    public Club() {
    }

    // Getters

    public int getId() {
        return id;
    }

    public Users getManager() {
        return manager;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean getIsActive(){
        return isActive;
    }

    // Setters

// public void setId(Long id) {
//         this.id = id;
//     }

    public void setManager(Users manager) {
        this.manager = manager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }
}