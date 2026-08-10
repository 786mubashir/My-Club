package com.club.club_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.club.club_management.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

   
    Optional<Role> findByName(String name);

    boolean existsByName(String name);

}