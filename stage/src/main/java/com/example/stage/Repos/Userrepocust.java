package com.example.stage.Repos;

import com.example.stage.entities.User;

import java.util.Optional;

public interface Userrepocust {

        Optional<User> findByEmail(String email);
    }

