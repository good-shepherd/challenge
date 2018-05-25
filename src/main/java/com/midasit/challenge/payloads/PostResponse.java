package com.midasit.challenge.payloads;

import com.midasit.challenge.entities.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostResponse {
    private Long userId;
    private String user;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<String> images = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}
