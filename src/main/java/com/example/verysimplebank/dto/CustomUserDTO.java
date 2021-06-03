package com.example.verysimplebank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class CustomUserDTO {
    private String login;

    public CustomUserDTO(String login) {
        this.login = login;
    }
}
