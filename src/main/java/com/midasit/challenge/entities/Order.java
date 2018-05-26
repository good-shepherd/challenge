package com.midasit.challenge.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "orders")
@Entity
public class Order extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> orderProducts = new HashSet<>();

    private boolean done;

    public Order(User customer, boolean done) {
        this.customer = customer;
        this.done = done;
    }
}
