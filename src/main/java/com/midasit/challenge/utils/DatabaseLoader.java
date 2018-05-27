package com.midasit.challenge.utils;

import com.midasit.challenge.entities.Product;
import com.midasit.challenge.entities.Role;
import com.midasit.challenge.entities.RoleName;
import com.midasit.challenge.entities.User;
import com.midasit.challenge.exceptions.AppException;
import com.midasit.challenge.repositories.*;
import com.midasit.challenge.services.PostService;
import com.midasit.challenge.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
    ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        //roleRepository.deleteAll();
        //roleRepository.save(new Role(RoleName.ROLE_UNCONFIRMED));
       // roleRepository.save(new Role(RoleName.ROLE_USER));
        //roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        //roleRepository.save(new Role(RoleName.ROLE_MASTER));
        // relationshipRepository.deleteAll();
        //userRepository.deleteAll();
        // postRepository.deleteAll();

    }
}
