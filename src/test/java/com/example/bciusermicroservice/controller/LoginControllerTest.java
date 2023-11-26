package com.example.bciusermicroservice.controller;

import com.example.bciusermicroservice.dto.PhoneDTO;
import com.example.bciusermicroservice.dto.UserDTO;
import com.example.bciusermicroservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    @Test
    void testLoginUserFound() {
        // Arrange
        String token = "validToken";
        UserDTO userDto = createValidUserDTO();
        Optional<UserDTO> userOptional = Optional.of(userDto);
        when(userService.getUserInfoFromToken(token)).thenReturn(userOptional);

        // Act
        ResponseEntity<?> response = loginController.login(token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLoginUserNotFound() {
        // Arrange
        String token = "invalidToken";
        when(userService.getUserInfoFromToken(token)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = loginController.login(token);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testLoginException() {
        // Arrange
        String token = "tokenWithException";
        when(userService.getUserInfoFromToken(token)).thenThrow(new RuntimeException("Simulación de una excepción"));

        // Act
        ResponseEntity<?> response = loginController.login(token);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private UserDTO createValidUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setCreated(LocalDateTime.now());
        userDTO.setLastLogin(LocalDateTime.now());
        userDTO.setToken("validToken");
        userDTO.setActive(true);
        userDTO.setName("John Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("validPassw45");

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber(123456789l);
        phoneDTO.setCityCode(7);
        phoneDTO.setCountrycode("25");

        List<PhoneDTO> phones = new ArrayList<>();
        phones.add(phoneDTO);

        userDTO.setPhones(phones);

        return userDTO;
    }
}
