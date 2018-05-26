package com.midasit.challenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "userPassword")
@Table(name = "users")
@Entity
public class User extends DateAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @JsonIgnore
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_birthdate")
    private LocalDate userBirthdate;

    @Column(name = "user_point")
    private int userPoint = 0;

    @Column(name = "user_confirmation_token")
    private String userConfirmationToken;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "follower")
    private Set<Relationship> followers = new HashSet<>();

    @OneToMany(mappedBy = "target")
    private Set<Relationship> targets = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();

    public User(String userEmail, String userPassword, String userName, LocalDate userBirthdate) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userBirthdate = userBirthdate;
    }
}
