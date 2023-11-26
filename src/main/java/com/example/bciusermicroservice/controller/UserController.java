package com.example.bciusermicroservice.controller;

import com.example.bciusermicroservice.dto.SignUpRequest;
import com.example.bciusermicroservice.dto.UserDTO;
import com.example.bciusermicroservice.model.ErrorResponse;
import com.example.bciusermicroservice.security.JwtUtil;
import com.example.bciusermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;

@RestController
@RequestMapping("/sign-up")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            UserDTO userResponse = userService.signUp(signUpRequest);
            String userEmail = jwtUtil.extractUsername(userResponse.getToken());
            System.out.println("Token JWT: " + userEmail);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                    Instant.now(), // timestamp
                    HttpStatus.BAD_REQUEST.value(), // código de error
                    e.getMessage() // detalle del error
            );

            ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(errorDetail));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
