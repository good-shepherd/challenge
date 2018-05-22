package com.midasit.challenge.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "relationships")
public class Relationship {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userOneId", column = @Column(name = "follower_id")),
            @AttributeOverride(name = "userTwoId", column = @Column(name = "target_id"))})
    private CompositePK id;

    @ManyToOne
    @JoinColumn(name = "follower_id", insertable = false, updatable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "target_id", insertable = false, updatable = false)
    private User target;

    private int status;

    private Long actionUserId;
}
