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
import org.springframework.web.multipart.MultipartFile;

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
        //System.out.println("image name: " + productRequest.getImage().getOriginalFilename());
        Product product = new Product(productRequest.getName(), productRequest.getPrice());
        //System.out.println("before: " + product.getProductImages().size());
        if (productRequest.getImage() != null) {
            product = addImageToProduct(product, productRequest);
        }
        //System.out.println("after: " + product.getProductImages().size());
        log.info("Saving - " + product.getProductName());
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException());
        /*Set<Image> images = product.getProductImages();
        List<String> pathList = new ArrayList<>();
        images.forEach(o -> pathList.add(o.getImageLocation()));
        uploadUtils.deleteFiles(pathList);*/
        product.setOnMenu(false);
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(ProductRequest productRequest, UserPrincipal userPrincipal, Long productId) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException());
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException());
        log.info("Saving - " + product.getProductName());
        //product = addImageToProduct(product, productRequest);
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

    public List<ProductResponse> findAllProduct(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> list = page.getContent();
        List<ProductResponse> res = new ArrayList<>();
        list.forEach(o -> res.add(productToResponse(o)));
        return res;
    }

    public List<ProductResponse> getMenu(Pageable pageable) {
        List<ProductResponse> res = findAllProduct(pageable);
        List<ProductResponse> list = new ArrayList<>();
        for (ProductResponse response : res) {
            if (response.isMenu()) {
                list.add(response);
            }
        }
        return list;
    }

    private ProductResponse productToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setMenu(product.isOnMenu());
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
        List<MultipartFile> l = productRequest.getImage();
        //l.add(productRequest.getImage());
        List<String> uploadedFiles = uploadUtils.saveFiles(l);
        Set<Image> images = new HashSet<>();
        uploadedFiles.forEach(o -> images.add(new Image(o, product)));
        product.setProductImages(images);
        return product;
    }

}
