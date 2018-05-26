package com.midasit.challenge.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductRequest {

    private String name;

    private int price;

    private boolean menu;

    @JsonIgnore
    private List<MultipartFile> image = new ArrayList<>();

}
