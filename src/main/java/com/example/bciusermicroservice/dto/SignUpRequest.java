package com.example.bciusermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;

    public SignUpRequest() {
    }
}
