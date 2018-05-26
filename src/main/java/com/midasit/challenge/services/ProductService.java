package com.midasit.challenge.services;

import com.midasit.challenge.entities.Image;
import com.midasit.challenge.entities.Product;
import com.midasit.challenge.entities.User;
import com.midasit.challenge.payloads.PostResponse;
import com.midasit.challenge.payloads.ProductRequest;
import com.midasit.challenge.payloads.ProductResponse;
import com.midasit.challenge.repositories.ProductRepository;
import com.midasit.challenge.repositories.UserRepository;
import com.midasit.challenge.security.UserPrincipal;
import com.midasit.challenge.utils.UploadUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    ProductRepository productRepository;
    UserRepository userRepository;
    UploadUtils uploadUtils;

    @Transactional
    public void uploadProduct(ProductRequest productRequest, UserPrincipal userPrincipal) {
        // a person who adds the item...
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException());
        Product product = new Product(productRequest.getName(), productRequest.getPrice());
        log.info("Saving - " + product.getProductName());
        productRepository.save(addImageToProduct(product, productRequest));
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException());
        Set<Image> images = product.getProductImages();
        List<String> pathList = new ArrayList<>();
        images.forEach(o -> pathList.add(o.getImageLocation()));
        uploadUtils.deleteFiles(pathList);
        productRepository.delete(product);
    }

    @Transactional
    public void updateProduct(ProductRequest productRequest, UserPrincipal userPrincipal, Long productId) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException());
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException());
        log.info("Saving - " + product.getProductName());
        product = addImageToProduct(product, productRequest);
        product.setProductName(productRequest.getName());
        product.setProductPrice(productRequest.getPrice());
        product.setOnMenu(productRequest.isMenu());
        productRepository.save(product);
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        ProductResponse response = productToResponse(product);
        return response;
    }

    public Page<Product> findAllProduct(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        return page;
    }

    private ProductResponse productToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getProductName());
        productResponse.setPrice(product.getProductPrice());
        productResponse.setCreatedDate(product.getCreatedDate());
        productResponse.setUpdatedDate(product.getUpdatedDate());
        Set<Image> images = product.getProductImages();
        List<String> imageUri = new ArrayList<>();
        if (images != null) {
            images.forEach(o -> imageUri.add(o.getImageLocation()));
            productResponse.setImages(imageUri);
        }
        productResponse.setImages(imageUri);
        return productResponse;
    }

    private Product addImageToProduct(Product product, ProductRequest productRequest) {
        if (productRequest.getImage() != null) {
            List<String> uploadedFiles = uploadUtils.saveFiles(productRequest.getImage());
            Set<Image> images = new HashSet<>();
            uploadedFiles.forEach(o -> images.add(new Image(o, product)));
            product.setProductImages(images);
        }
        return product;
    }

}
