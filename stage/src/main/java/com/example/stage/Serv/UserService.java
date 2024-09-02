package com.example.stage.Serv;

import java.time.LocalDate;

import com.example.stage.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
public interface UserService  extends UserDetailsService {
    void registerUser(String password, String email, String phonenum, LocalDate datebirth, String firstname, String lastname, String role);
    User findUserByEmail(String email);
    void updateUserDetails(Long userid, String email, String phonenum, String firstname, String lastname, LocalDate datebirth);
    void deleteUserById(Long userid);
}