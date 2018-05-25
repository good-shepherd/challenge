package com.midasit.challenge.repositories;

import com.midasit.challenge.entities.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}
