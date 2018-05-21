package com.midasit.challenge.controllers;

import com.midasit.challenge.security.CurrentUser;
import com.midasit.challenge.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String helloUser(@CurrentUser UserPrincipal currentUser) {
        return "hello " + currentUser.getEmail();
    }

    @GetMapping
    public String helloAll() {
        return "hello world!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String helloAdmin() {
        return "hello admin!";
    }

}

