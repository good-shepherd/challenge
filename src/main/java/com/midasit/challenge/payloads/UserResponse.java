package com.midasit.challenge.payloads;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserResponse {
    private String email;
    private String name;
    private LocalDate birthdate;
    private int point;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}