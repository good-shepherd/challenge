package com.midasit.challenge.repositories;

import com.midasit.challenge.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderRepository extends JpaRepository<Order, Long> {

}
