package com.example.stage.Repos;
import com.example.stage.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    // Method to find a role by its name
    Optional<Role> findByRoleName(String roleName);

    // Additional custom queries can be added here if needed
}
