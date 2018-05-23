package com.midasit.challenge.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "posts")
@Entity
public class Post extends DateAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title")
    private String postTitle;

    @Column(columnDefinition = "text", name = "post_content")
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;
}
