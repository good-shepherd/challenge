package com.midasit.challenge.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<String> productId = new ArrayList<>();
    private List<String> quantity = new ArrayList<>();
}
