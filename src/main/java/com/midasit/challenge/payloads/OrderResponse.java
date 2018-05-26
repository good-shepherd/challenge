package com.midasit.challenge.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private Long customerId;
    private String customerName;
    private boolean done;
    private LocalDateTime updatedTime;
}
