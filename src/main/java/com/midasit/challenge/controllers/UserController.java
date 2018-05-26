package com.midasit.challenge.controllers;


import com.midasit.challenge.entities.User;
import com.midasit.challenge.payloads.UserResponse;
import com.midasit.challenge.payloads.UserUpdateRequest;
import com.midasit.challenge.security.CurrentUser;
import com.midasit.challenge.security.UserPrincipal;
import com.midasit.challenge.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MASTER')")
    public UserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getId());
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public Page<User> getAllUser() {
        Pageable pageable = PageRequest.of(0, 10);
        return userService.findAllUser(pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public void updateUserByAdmin(UserUpdateRequest request, @PathVariable Long id) {
        userService.updateUserByAdmin(request, id);
    }

    @PutMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MASTER')")
    public void updateUserByHimself(UserUpdateRequest request, @CurrentUser UserPrincipal userPrincipal) {
        userService.updateUserByHimself(request, userPrincipal.getId());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MAST                                                                                                                                                  ER')")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

}
