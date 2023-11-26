package com.example.bciusermicroservice.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SignUpRequestTest {

    @Test
    void signUpRequestConstructorAndGetters() {
        // Arrange
        String name = "John Doe";
        String email = "john@example.com";
        String password = "securePassword";
        List<PhoneDTO> phones = Arrays.asList(
                new PhoneDTO(123456789L, 1, "2")
        );

        // Act
        SignUpRequest signUpRequest = new SignUpRequest(name, email, password, phones);

        // Assert
        assertEquals(name, signUpRequest.getName());
        assertEquals(email, signUpRequest.getEmail());
        assertEquals(password, signUpRequest.getPassword());
        assertEquals(phones, signUpRequest.getPhones());
    }

}

