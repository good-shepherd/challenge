package com.midasit.challenge.services;

import com.midasit.challenge.entities.Role;
import com.midasit.challenge.entities.RoleName;
import com.midasit.challenge.entities.User;
import com.midasit.challenge.payloads.UserResponse;
import com.midasit.challenge.payloads.UserUpdateRequest;
import com.midasit.challenge.repositories.RoleRepository;
import com.midasit.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        UserResponse response = new UserResponse(user.getUserEmail(), user.getUserName(), user.getUserBirthdate(), user.getUserPoint(),
                user.getCreatedDate(), user.getUpdatedDate());
        return response;
    }
    public Page<User> findAllUser(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return page;
    }
    @Transactional
    public void updateRoleById(Long userId, RoleName roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());
        Role role = roleRepository.findByName(roleName).get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
    }
    @Transactional
    public void updateUserByHimself(UserUpdateRequest request) { //name, date
        User user = userRepository.findByUserEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException());
        user.setUserName(request.getName());
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // "1990-08-22" redundant
        user.setUserBirthdate(LocalDate.parse(request.getBirthdate()));
        userRepository.save(user);
    }

    @Transactional
    public void updateUserByAdmin(UserUpdateRequest request) {
        User user = userRepository.findByUserEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException());
        user.setUserName(request.getName());
        user.setUserBirthdate(LocalDate.parse(request.getBirthdate()));
        user.setUserEmail(request.getEmail());
        user.setUserPoint(request.getPoint());
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
