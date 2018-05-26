package com.midasit.challenge.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String email;
    private String password;
    private String name;
    private String birthdate; // "1990-08-22"
    private int point;
}
