package com.midasit.challenge.controllers;

import com.midasit.challenge.payloads.ApiResponse;
import com.midasit.challenge.payloads.PostRequest;
import com.midasit.challenge.payloads.PostResponse;
import com.midasit.challenge.security.CurrentUser;
import com.midasit.challenge.security.UserPrincipal;
import com.midasit.challenge.services.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/posts")
public class PostController {

    PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> uploadPost(@ModelAttribute PostRequest postRequest, @CurrentUser UserPrincipal currentUser) {
        if (postRequest.getTitle() == null || postRequest.getContent() == null) {
            return ResponseEntity.ok(new ApiResponse(false, "title or content is missing."));
        }
        postService.uploadPost(postRequest, currentUser);
        return ResponseEntity.ok(new ApiResponse(true, "Successfully uploaded"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("(@postService.findById(#postId).getUserId() == #currentUser.getId()) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deletePost(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse(true, "Post deleted successfully."));
    }

    @GetMapping("/{id}")
    public PostResponse findById(@PathVariable(value = "id") Long postId) {
        return postService.findById(postId);
    }

}
