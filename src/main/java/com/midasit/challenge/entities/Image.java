package com.midasit.challenge.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "images")
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_location")
    private String imageLocation;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Image(String imageLocation, Post post) {
        this.imageLocation = imageLocation;
        this.post = post;
    }
}
