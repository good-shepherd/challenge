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
@Table(name = "products")
@Entity
public class Product extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "product_isonmenu")
    private boolean isOnMenu = true;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Image> productImages = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> orderProducts = new HashSet<>();

    public Product(String productName, int productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
