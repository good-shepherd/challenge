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
    private Long id;
    private String email;
    private String name;
    private LocalDate birthdate;
    private int point;
    private String role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
