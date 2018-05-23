package com.midasit.challenge;

import com.midasit.challenge.entities.Role;
import com.midasit.challenge.entities.RoleName;
import com.midasit.challenge.repositories.PostRepository;
import com.midasit.challenge.repositories.RoleRepository;
import com.midasit.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
@AllArgsConstructor
public class ChallengeApplication implements CommandLineRunner {

    RoleRepository roleRepository;
    UserRepository userRepository;
    PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // roleRepository.save(new Role(RoleName.ROLE_UNCONFIRMED));
        // roleRepository.save(new Role(RoleName.ROLE_USER));
        // roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        // userRepository.deleteAll();
        postRepository.deleteAll();
    }
}
