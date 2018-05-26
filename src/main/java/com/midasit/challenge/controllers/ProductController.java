package com.midasit.challenge.controllers;

import com.midasit.challenge.entities.Image;
import com.midasit.challenge.entities.Product;
import com.midasit.challenge.payloads.ApiResponse;
import com.midasit.challenge.payloads.ProductRequest;
import com.midasit.challenge.payloads.ProductResponse;
import com.midasit.challenge.repositories.ImageRepository;
import com.midasit.challenge.repositories.ProductRepository;
import com.midasit.challenge.security.CurrentUser;
import com.midasit.challenge.security.UserPrincipal;
import com.midasit.challenge.services.ProductService;
import com.midasit.challenge.utils.UploadUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;
    ProductRepository productRepository;
    ImageRepository imageRepository;
    UploadUtils uploadUtils;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public ResponseEntity<ApiResponse> uploadProduct(@ModelAttribute ProductRequest productRequest, @CurrentUser UserPrincipal userPrincipal) {
        if (productRequest.getName() == null || productRequest.getPrice() < 0) {
            return ResponseEntity.ok(new ApiResponse(false, "name or price is missing."));
        }
        productService.uploadProduct(productRequest, userPrincipal);
        return ResponseEntity.ok(new ApiResponse(true, "Product Successfully uploaded"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public ResponseEntity<ApiResponse> deleteProduct(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse(true, "Product deleted successfully."));
    }

    @Transactional
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public void uploadImage(List<MultipartFile> files, @PathVariable Long id) {
        System.out.println("-----------------");
        System.out.println(files.size());
        files.forEach(o-> System.out.println(o.getOriginalFilename()));
        System.out.println("-----------------");
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        List<String> list = uploadUtils.saveFiles(files);
        List<Image> images = new ArrayList<>();
        for (String image : list) {
            Image img = new Image(image, product);
            images.add(img);
        }
        product.setProductImages(new HashSet<>(images));
        productRepository.save(product);
    }

    /*@PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public ResponseEntity<ApiResponse> updateProduct(@ModelAttribute ProductRequest productRequest, @CurrentUser UserPrincipal userPrincipal,
                                                     @PathVariable(value = "id") Long productId) {
        System.out.println("req: " + productRequest.toString());
        if (productRequest.getName() == null || productRequest.getPrice() < 0) {
            return ResponseEntity.ok(new ApiResponse(false, "name or price is missing."));
        }
        productService.updateProduct(productRequest, userPrincipal, productId);
        return ResponseEntity.ok(new ApiResponse(true, "Product Successfully updated"));
    }*/

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable(value = "id") Long productId) {
        return productService.findById(productId);
    }

    @GetMapping
    public List<ProductResponse> findAll(@RequestParam(value = "page", required = false) int page,
                                         @RequestParam(value = "size", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.findAllProduct(pageable);
    }

    @GetMapping("/menu")
    public List<ProductResponse> getMenu(@RequestParam(value = "page", required = false) int page,
                                         @RequestParam(value = "size", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getMenu(pageable);
    }


}
