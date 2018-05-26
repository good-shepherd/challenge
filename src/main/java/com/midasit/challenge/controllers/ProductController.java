package com.midasit.challenge.controllers;

import com.midasit.challenge.entities.Product;
import com.midasit.challenge.payloads.ApiResponse;
import com.midasit.challenge.payloads.ProductRequest;
import com.midasit.challenge.payloads.ProductResponse;
import com.midasit.challenge.security.CurrentUser;
import com.midasit.challenge.security.UserPrincipal;
import com.midasit.challenge.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MASTER')")
    public ResponseEntity<ApiResponse> uploadProduct(@ModelAttribute ProductRequest productRequest, @CurrentUser UserPrincipal userPrincipal) {
        if (productRequest.getName() == null || productRequest.getPrice() < 0) {
            return ResponseEntity.ok(new ApiResponse(false, "name or price is missing."));
        }
        productService.uploadProduct(productRequest, userPrincipal);
        return ResponseEntity.ok(new ApiResponse(true, "Product Successfully uploaded"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MASTER')")
    public ResponseEntity<ApiResponse> deleteProduct(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse(true, "Product deleted successfully."));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MASTER')")
    public ResponseEntity<ApiResponse> updateProduct(@ModelAttribute ProductRequest productRequest, @CurrentUser UserPrincipal userPrincipal,
                                                     @PathVariable(value = "id") Long productId) {
        if (productRequest.getName() == null || productRequest.getPrice() < 0) {
            return ResponseEntity.ok(new ApiResponse(false, "name or price is missing."));
        }
        productService.updateProduct(productRequest, userPrincipal, productId);
        return ResponseEntity.ok(new ApiResponse(true, "Product Successfully updated"));
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable(value = "id") Long productId) {
        return productService.findById(productId);
    }

    @GetMapping
    public Page<Product> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        return productService.findAllProduct(pageable);
    }



}
