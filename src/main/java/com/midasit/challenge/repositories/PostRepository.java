package com.midasit.challenge.repositories;

import com.midasit.challenge.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;


public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    Post save(Post s);
}
