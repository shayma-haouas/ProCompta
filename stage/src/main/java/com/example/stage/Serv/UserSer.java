package com.example.stage.Serv;

import com.example.stage.Repos.RoleRepo;
import com.example.stage.Repos.UserRepo;
import com.example.stage.entities.Role;
import com.example.stage.entities.User;
import com.example.stage.entities.Role;
import com.example.stage.entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserSer implements UserService {
    private final UserRepo userRepository;
    private final RoleRepo roleRepository; // Assuming you have a RoleRepo
    private final PasswordEncoder passwordEncoder;

    public UserSer(UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    //updateedetails lezm khtrhy declaraer abstarct
    @Override
    public void updateUserDetails(Long userid, String email, String phonenum,
                                  String firstname, String lastname, LocalDate datebirth) {
        // Find the user by ID
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userid));

        // Update the user's details
        user.setEmail(email);
        user.setPhonenum(phonenum);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setDatebirth(datebirth);

        // Save the updated user
        userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }



//delteeeee

    @Override
    public void deleteUserById(Long userid) {
        // Check if the user exists
        if (!userRepository.existsById(userid)) {
            throw new UsernameNotFoundException("User not found with ID: " + userid);
        }

        // Delete the user by ID
        userRepository.deleteById(userid);
    }


    @Override
    public void registerUser(String password, String email, String phonenum,
                             LocalDate datebirth, String firstname, String lastname, String role) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhonenum(phonenum);
        user.setDatebirth(datebirth);
        user.setFirstname(firstname);
        user.setLastname(lastname);

        // If role is not provided or invalid, default to 'client'
        if (role == null || role.isEmpty() || (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("client"))) {
            role = "client";
        }

        Optional<Role> roleOptional = findOrCreateRole(role);
        roleOptional.ifPresent(user.getRoles()::add);

        userRepository.save(user);
    }

    private Optional<Role> findOrCreateRole(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .or(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName(roleName);
                    return Optional.of(roleRepository.save(newRole));
                });
    }
}