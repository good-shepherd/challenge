package com.midasit.challenge.services;

import com.midasit.challenge.entities.User;
import com.midasit.challenge.payloads.ProductRequest;
import com.midasit.challenge.repositories.ProductRepository;
import com.midasit.challenge.repositories.UserRepository;
import com.midasit.challenge.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    ProductRepository productRepository;
    UserRepository userRepository;

    public void uploadProduct(ProductRequest productRequest, UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException());
    }
    public void deleteProduct() {

    }

    public void updateProduct() {

    }

    public void findProductById() {

    }

    public void findAllProduct() {

    }
}
