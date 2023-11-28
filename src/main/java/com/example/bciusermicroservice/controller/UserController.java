package com.example.bciusermicroservice.controller;

import com.example.bciusermicroservice.dto.SignUpRequest;
import com.example.bciusermicroservice.dto.UserDTO;
import com.example.bciusermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        UserDTO userResponse = userService.signUp(signUpRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

}
