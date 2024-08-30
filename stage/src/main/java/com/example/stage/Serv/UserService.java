package com.example.stage.Serv;

import java.time.LocalDate;

import com.example.stage.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
public interface UserService extends UserDetailsService {

    // Method to register a new user
    void registerUser(String password, String email, String phonenum,
                      LocalDate datebirth, String firstname, String lastname, String role);

    // Add other user-related methods as needed, for example:

    // Method to find a user by email
    User findUserByEmail(String email);

    // Method to update user details
    void updateUserDetails(Long userid, String email, String phonenum,
                           String firstname, String lastname, LocalDate datebirth);

    // Method to delete a user by ID
    void deleteUserById(Long userid);
}
