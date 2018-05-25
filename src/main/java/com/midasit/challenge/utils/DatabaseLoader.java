package com.midasit.challenge.utils;

import com.midasit.challenge.repositories.ImageRepository;
import com.midasit.challenge.repositories.PostRepository;
import com.midasit.challenge.repositories.RoleRepository;
import com.midasit.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@AllArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    RoleRepository roleRepository;
    UserRepository userRepository;
    PostRepository postRepository;
    ImageRepository imageRepository;

    @Override
    public void run(String... args) throws Exception {
        // roleRepository.save(new Role(RoleName.ROLE_UNCONFIRMED));
        // roleRepository.save(new Role(RoleName.ROLE_USER));
        // roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        // userRepository.deleteAll();
        //postRepository.deleteAll();
        //imageRepository.deleteAll();
    }
}
