package com.midasit.challenge.services;

import com.midasit.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;

}
