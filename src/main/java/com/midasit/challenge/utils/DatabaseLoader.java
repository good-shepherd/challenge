package com.midasit.challenge.utils;

import com.midasit.challenge.entities.Role;
import com.midasit.challenge.entities.RoleName;
import com.midasit.challenge.entities.User;
import com.midasit.challenge.exceptions.AppException;
import com.midasit.challenge.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Component
public class DatabaseLoader implements CommandLineRunner {

    RoleRepository roleRepository;
    UserRepository userRepository;
    PostRepository postRepository;
    ImageRepository imageRepository;
    RelationshipRepository relationshipRepository;

    @Override
    public void run(String... args) throws Exception {
        /*roleRepository.deleteAll();
        roleRepository.save(new Role(RoleName.ROLE_UNCONFIRMED));
        roleRepository.save(new Role(RoleName.ROLE_USER));
        roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        roleRepository.save(new Role(RoleName.ROLE_MASTER));*/
        // relationshipRepository.deleteAll();
        //userRepository.deleteAll();
        // postRepository.deleteAll();
        /*User supUser = new User("master@fakemail.com", "1234567",
                "superuser", LocalDate.of(1990, 9, 29));*/
        /*Role userRole = roleRepository.findByName(RoleName.ROLE_MASTER).get();

        supUser.setRoles(Collections.singleton(userRole));*/
        //System.out.println(supUser.getRoles().toString());
        // userRepository.save(supUser);

    }
}
