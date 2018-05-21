package com.midasit.challenge.controllers;

import com.midasit.challenge.entities.Role;
import com.midasit.challenge.entities.RoleName;
import com.midasit.challenge.entities.User;
import com.midasit.challenge.exceptions.AppException;
import com.midasit.challenge.payloads.ApiResponse;
import com.midasit.challenge.payloads.JwtAuthenticationResponse;
import com.midasit.challenge.payloads.LoginRequest;
import com.midasit.challenge.payloads.SignUpRequest;
import com.midasit.challenge.repositories.RoleRepository;
import com.midasit.challenge.repositories.UserRepository;
import com.midasit.challenge.security.JwtTokenProvider;
import com.midasit.challenge.services.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;
    EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        if (userRepository.existsByUserEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address is already taken!"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        /*switch (signUpRequest.getRole()) {
            case 1:
                role = RoleName.ROLE_USER;
                break;
            case 2:
                role = RoleName.ROLE_ADMIN;
                break;
            default:
                log.error("default case occurred");
                break;
        }*/
        Role userRole = roleRepository.findByName(RoleName.ROLE_UNCONFIRMED)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));
        user.setUserConfirmationToken(UUID.randomUUID().toString());
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(result.getId()).toUri();
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getUserEmail());
        registrationEmail.setSubject("registration confirmation");

        // need to fix the URL below
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + request.getScheme() + "://" + request.getServerName() + "/confirm?t=" + user.getUserConfirmationToken());
        registrationEmail.setFrom("noreply@domain.com");
        emailService.sendEmail(registrationEmail);
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully. Check your email and confirm it."));
    }

    @GetMapping("/confirm")
    @Transactional
    public ResponseEntity<ApiResponse> confirmUser(@RequestParam("t") String token) {
        if (!userRepository.findByUserConfirmationToken(token).isPresent()) {
            new ResponseEntity(new ApiResponse(false, "The token is not valid!"), HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findByUserConfirmationToken(token).orElseThrow(() -> new AppException("User doesn't exist."));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User Role not set."));
        Set<Role> set = new HashSet<>();
        set.add(userRole);
        user.setRoles(set);
        user.setUserConfirmationToken("");
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "User confirmed successfully."));
    }
}
