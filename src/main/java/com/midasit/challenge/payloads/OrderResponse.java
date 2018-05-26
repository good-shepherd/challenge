package com.midasit.challenge.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private Long customerId;
    private String customerName;
    private boolean done;
    private int sumOfPrice;
    private List<Integer> quantity;
    private List<String> productName;
    private LocalDateTime updatedTime;
}
