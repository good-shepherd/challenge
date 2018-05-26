package com.midasit.challenge.repositories;

import com.midasit.challenge.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
