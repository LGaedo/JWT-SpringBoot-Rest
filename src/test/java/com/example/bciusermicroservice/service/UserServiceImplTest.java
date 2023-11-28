package com.example.bciusermicroservice.service;

import com.example.bciusermicroservice.dto.PhoneDTO;
import com.example.bciusermicroservice.dto.SignUpRequest;
import com.example.bciusermicroservice.dto.UserDTO;
import com.example.bciusermicroservice.exception.SignUpException;
import com.example.bciusermicroservice.model.User;
import com.example.bciusermicroservice.repository.UserRepository;
import com.example.bciusermicroservice.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void signUp_Success() {
        // Simula el comportamiento del repositorio
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // Simula el comportamiento del método de codificación de contraseña
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encoded-password");

        // Configura el objeto SignUpRequest
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Test User");
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("Tstpasswrd13");
        signUpRequest.setPhones(Collections.singletonList(new PhoneDTO(123456789L, 7, "25")));

        // Llama al método signUp
        UserDTO result = userService.signUp(signUpRequest);

        // Verifica que el resultado sea el esperado
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void signUp_UserAlreadyExists() {
        // Simula el comportamiento del repositorio cuando el usuario ya existe
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new User()));

        // Configura el objeto SignUpRequest
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("NombreUsuario");
        signUpRequest.setEmail("usuario@example.com");
        signUpRequest.setPassword("Contraseñ1a2");

        // Act & Assert
        assertThrows(SignUpException.class, () -> {
            userService.signUp(signUpRequest);
        });

    }

}
