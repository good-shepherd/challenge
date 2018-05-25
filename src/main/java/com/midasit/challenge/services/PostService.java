package com.midasit.challenge.services;

import com.midasit.challenge.entities.Comment;
import com.midasit.challenge.entities.Image;
import com.midasit.challenge.entities.Post;
import com.midasit.challenge.entities.User;
import com.midasit.challenge.payloads.PostRequest;
import com.midasit.challenge.payloads.PostResponse;
import com.midasit.challenge.repositories.PostRepository;
import com.midasit.challenge.repositories.UserRepository;
import com.midasit.challenge.security.UserPrincipal;
import com.midasit.challenge.utils.UploadUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private UploadUtils uploadUtils;

    // need a feature that deletes files when rollbacking
    @Transactional
    public void uploadPost(PostRequest postRequest, UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException());
        Post post = new Post(postRequest.getTitle(), postRequest.getContent(), user);
        if (postRequest.getImage() != null) {
            List<String> uploadedFiles = uploadUtils.saveFiles(postRequest.getImage());
            Set<Image> images = new HashSet<>();
            uploadedFiles.forEach(o -> images.add(new Image(o, post)));
            post.setPostImages(images);
        }
        log.info("Saving - " + post.getPostTitle());
        postRepository.save(post);
        addToEsIndex(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException());
        Set<Image> images = post.getPostImages();
        List<String> pathList = new ArrayList<>();
        images.forEach(o -> pathList.add(o.getImageLocation()));
        uploadUtils.deleteFiles(pathList);
        postRepository.delete(post);
    }

    @Transactional
    public PostResponse findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException());
        return postToResponse(post);
    }

    private PostResponse postToResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setUserId(post.getPostWriter().getId());
        postResponse.setTitle(post.getPostTitle());
        postResponse.setContent(post.getPostContent());
        postResponse.setUser(post.getPostWriter().getUserEmail());
        postResponse.setCreatedDate(post.getCreatedDate());
        postResponse.setUpdatedDate(post.getUpdatedDate());
        Set<Image> images = post.getPostImages();
        List<String> imageUri = new ArrayList<>();
        Set<Comment> comments = post.getPostComments();
        if (images != null) {
            images.forEach(o -> imageUri.add(o.getImageLocation()));
            postResponse.setImages(imageUri);
        }
        if (comments != null) postResponse.setComments(new ArrayList<>(comments));
        return postResponse;
    }

    private void addToEsIndex(Post post) {
        PostResponse postResponse = postToResponse(post);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(50000).setReadTimeout(100000).build();
        bodyMap.add("user", postResponse.getUser());
        bodyMap.add("title", postResponse.getTitle());
        bodyMap.add("content", postResponse.getContent());
        bodyMap.add("create-date", postResponse.getCreatedDate().toString());
        bodyMap.add("update-date", postResponse.getUpdatedDate().toString());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        String api = "http://192.168.0.7:9200/blog-tests/test/" + post.getId();
        ResponseEntity<String> response = restTemplate.exchange(api, HttpMethod.PUT, requestEntity, String.class);
        System.out.println("elasticsearch PUT: " + response.toString());
    }
}
