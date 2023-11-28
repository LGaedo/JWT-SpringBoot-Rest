package com.example.bciusermicroservice.controller;

import com.example.bciusermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String token) {
        return userService.getUserByUsername(token)
                .map(userDto -> ResponseEntity.ok().body(userDto.toOrderedMap()))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado para el token proporcionado"));
    }
}
