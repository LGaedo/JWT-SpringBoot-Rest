package com.example.bciusermicroservice.service;

import com.example.bciusermicroservice.dto.SignUpRequest;
import com.example.bciusermicroservice.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    UserDTO signUp(SignUpRequest signUpRequest);
    Optional<UserDTO> getUserByUsername(String token);
}
