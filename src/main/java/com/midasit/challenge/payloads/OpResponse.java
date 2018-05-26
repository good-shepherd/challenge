package com.midasit.challenge.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OpResponse {
    private Long orderId;
    private List<Long> productId;
    private List<String> productName;
}
