package com.midasit.challenge.controllers;


import com.midasit.challenge.payloads.UserResponse;
import com.midasit.challenge.security.CurrentUser;
import com.midasit.challenge.security.UserPrincipal;
import com.midasit.challenge.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    UserService userService;

    public UserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {

        return new UserResponse()
        }
}
