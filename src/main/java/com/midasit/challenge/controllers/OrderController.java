package com.midasit.challenge.controllers;

import com.midasit.challenge.entities.Order;
import com.midasit.challenge.entities.OrderProduct;
import com.midasit.challenge.entities.User;
import com.midasit.challenge.payloads.ApiResponse;
import com.midasit.challenge.payloads.OpResponse;
import com.midasit.challenge.payloads.OrderRequest;
import com.midasit.challenge.payloads.OrderResponse;
import com.midasit.challenge.security.CurrentUser;
import com.midasit.challenge.security.UserPrincipal;
import com.midasit.challenge.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/orders")
public class OrderController {

    OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MASTER')")
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody OrderRequest orderRequest, @CurrentUser UserPrincipal userPrincipal) {
        orderService.placeOrder(orderRequest, userPrincipal);
        return ResponseEntity.ok(new ApiResponse(true, "Order Successfully placed"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public List<OrderResponse> findAllOrder() {
        return orderService.findAllOrder();
    }

    @GetMapping("/notdone")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public List<OrderResponse> getNotDoneOrder() {
        return orderService.getNotDoneOrder();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MASTER')")
    public OpResponse findOPbyId(@PathVariable Long id) {
        return orderService.findOpById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MASTER')")
    public List<OrderResponse> findMyOrder(@CurrentUser UserPrincipal userPrincipal) {
        return orderService.findMyOrder(userPrincipal);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse> handleOrder(@PathVariable Long id) {
        orderService.handleOrder(id);
        return ResponseEntity.ok(new ApiResponse(true, "Order Successfully handled"));
    }



}
