package com.midasit.challenge.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OpResponse {
    private Long orderId;
    private List<Long> productId;
    private List<String> productName;
}
