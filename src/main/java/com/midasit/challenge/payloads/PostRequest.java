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
public class PostRequest {

    private String title;

    private String content;

    @JsonIgnore
    private List<MultipartFile> image = new ArrayList<>();
}
