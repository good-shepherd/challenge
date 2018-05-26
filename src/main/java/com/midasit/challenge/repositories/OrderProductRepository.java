package com.midasit.challenge.repositories;

import com.midasit.challenge.entities.CompositePK;
import com.midasit.challenge.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderProductRepository extends JpaRepository<OrderProduct, CompositePK> {

    @Query(value = "SELECT * FROM ORDERPRODUCTS O WHERE O.RDER_ID = ?1", nativeQuery = true)
    OrderProduct getOneOP(Long id);
}
