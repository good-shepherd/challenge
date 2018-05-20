package com.midasit.challenge.repositories;

import com.midasit.challenge.entities.Role;
import com.midasit.challenge.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
