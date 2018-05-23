package com.midasit.challenge.eventhandlers;

import com.midasit.challenge.entities.Post;
import com.midasit.challenge.entities.User;
import com.midasit.challenge.repositories.UserRepository;
import com.midasit.challenge.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
@RepositoryEventHandler(Post.class)
public class PostCreationEventHandler {

    private final UserRepository userRepository;

    @HandleBeforeCreate
    @HandleBeforeSave
    public void applyUserInfo(Post post) {
        UserPrincipal userPrincipal;
        try {
            userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            return;
        }
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException());
        if (post.getWriter() != null) {
            if (post.getWriter().getId() != user.getId()) return;
        } else {
            log.info(user.getUserEmail() + "added to the post.");
            post.setWriter(user);
        }
    }
}
