package com.midasit.challenge.repositories;

import com.midasit.challenge.entities.CompositePK;
import com.midasit.challenge.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, CompositePK> {
}
