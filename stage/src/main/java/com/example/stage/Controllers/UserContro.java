package com.example.stage.Controllers;


import com.example.stage.DTO.UserregistrationDTO;
import com.example.stage.Serv.UserService;
import com.example.stage.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
public class UserContro {

    private final UserService userService;

    public UserContro(UserService userService) {
        this.userService = userService;
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserregistrationDTO userDto) {
        try {
            userService.registerUser(
                    userDto.getPassword(),
                    userDto.getEmail(),
                    userDto.getPhonenum(),
                    userDto.getDatebirth(),
                    userDto.getFirstname(),
                    userDto.getLastname(),
                    userDto.getRole()
            );
            return new ResponseEntity<>("User registered successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error registering user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // Endpoint to update user details
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserDetails(@PathVariable Long userid,
                                                    @RequestParam String email,
                                                    @RequestParam String phonenum,
                                                    @RequestParam String firstname,
                                                    @RequestParam String lastname,
                                                    @RequestParam LocalDate datebirth) {
        try {
            userService.updateUserDetails(userid, email, phonenum, firstname, lastname, datebirth);
            return new ResponseEntity<>("User details updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user details: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userid) {
        try {
            userService.deleteUserById(userid);
            return new ResponseEntity<>("User deleted successfully.", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to find a user by email
    @GetMapping("/find")
    public ResponseEntity<User> findUserByEmail(@RequestParam String email) {
        try {
            User user = userService.findUserByEmail(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}