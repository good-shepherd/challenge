package com.midasit.challenge.controllers;


import com.midasit.challenge.payloads.OrderResponse;
import com.midasit.challenge.payloads.UserResponse;
import com.midasit.challenge.payloads.UserUpdateRequest;
import com.midasit.challenge.security.CurrentUser;
import com.midasit.challenge.security.UserPrincipal;
import com.midasit.challenge.services.OrderService;
import com.midasit.challenge.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    UserService userService;
    OrderService orderService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MASTER')")
    public UserResponse getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getId());
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public List<UserResponse> getAllUser(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.findAllUser(pageable);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public void updateUserByAdmin(UserUpdateRequest request, @PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        userService.updateUserByAdmin(request, id, userPrincipal.getId());
    }

    @PostMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MASTER')")
    public void updateUserByHimself(UserUpdateRequest request, @CurrentUser UserPrincipal userPrincipal) {
        userService.updateUserByHimself(request, userPrincipal.getId());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER') or (#id == #userPrincipal.getId())")
    public void deleteUser(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        userService.deleteUser(id, userPrincipal.getId());
    }

    // api/users/1/orders/1999/08
    @GetMapping("/{id}/orders/{yyyy}/{MM}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MASTER')")
    public List<OrderResponse> getOrder(@PathVariable Long id, @PathVariable int yyyy, @PathVariable int MM) {
        return userService.findOrderByDate(id, yyyy, MM);
    }

}
