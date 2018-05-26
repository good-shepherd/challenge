package com.midasit.challenge.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "posts")
@Entity
public class Post extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title")
    private String postTitle;

    @Column(columnDefinition = "text", name = "post_content")
    private String postContent;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> postComments = new HashSet<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Image> postImages = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User postWriter;

    public Post(String postTitle, String postContent, User postWriter) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postWriter = postWriter;
    }
}
