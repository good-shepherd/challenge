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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        Set<Role> roles = user.getRoles();
        List<String> sroles = new ArrayList<>();
        roles.forEach(o -> sroles.add(o.getName().toString()));
        UserResponse response = new UserResponse(user.getUserEmail(), user.getUserName(), user.getUserBirthdate(), user.getUserPoint(), sroles.get(0),
                user.getCreatedDate(), user.getUpdatedDate());
        return response;
    }

    public List<UserResponse> findAllUser(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        List<User> list = page.getContent();
        List<UserResponse> res = new ArrayList<>();
        list.forEach(o -> res.add(userToResponse(o)));
        return res;
    }

    @Transactional
    public void updateRoleById(Long userId, RoleName roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());
        Role role = roleRepository.findByName(roleName).get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
    }

    @Transactional
    public void updateUserByHimself(UserUpdateRequest request, Long id) { //name, date
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        user.setUserName(request.getName());
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // "1990-08-22" redundant
        user.setUserBirthdate(LocalDate.parse(request.getBirthdate()));
        userRepository.save(user);
    }

    @Transactional
    public void updateUserByAdmin(UserUpdateRequest request, Long id, Long adminId) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        user.setUserName(request.getName());
        user.setUserBirthdate(LocalDate.parse(request.getBirthdate()));
        user.setUserEmail(request.getEmail());
        user.setUserPoint(request.getPoint());
        userRepository.save(user);
    }

    public void deleteUser(Long id, Long adminId) {
        userRepository.deleteById(id);
    }

    private List<String> getRoleStrings(User user) {
        Set<Role> roles = user.getRoles();
        List<String> sroles = new ArrayList<>();
        roles.forEach(o -> sroles.add(o.getName().toString()));
        return sroles;
    }

    private UserResponse userToResponse(User user) {
        UserResponse res = new UserResponse(user.getUserEmail(), user.getUserName(), user.getUserBirthdate(), user.getUserPoint(),
                getRoleStrings(user).get(0), user.getCreatedDate(), user.getUpdatedDate());
        return res;
    }
}
