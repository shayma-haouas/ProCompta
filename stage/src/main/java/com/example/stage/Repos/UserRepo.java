package com.example.stage.Repos;

import com.example.stage.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long>,  Userrepocust {
    Optional<User> findByEmail(String email);

        // Method to find a user by phone number (if needed)
        Optional<User> findByPhonenum(String phonenum);

        // Additional custom queries can be added here
    }

