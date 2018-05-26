package com.midasit.challenge.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "orderproducts")
public class OrderProduct {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userOneId", column = @Column(name = "order_id")),
            @AttributeOverride(name = "userTwoId", column = @Column(name = "product_id"))})
    private CompositePK id;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    private int quantity;

}
