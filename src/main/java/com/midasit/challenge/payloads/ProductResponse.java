package com.midasit.challenge.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductResponse {
    // private Long userId;
    private Long id;
    private String name;
    private int price;
    private boolean menu;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<String> images = new ArrayList<>();
    private int quantity = 0;
}
