package com.example.bciusermicroservice.service;

import com.example.bciusermicroservice.dto.PhoneDTO;
import com.example.bciusermicroservice.dto.SignUpRequest;
import com.example.bciusermicroservice.dto.UserDTO;
import com.example.bciusermicroservice.model.Phone;
import com.example.bciusermicroservice.model.User;
import com.example.bciusermicroservice.repository.UserRepository;
import com.example.bciusermicroservice.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
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

        // Simula el comportamiento del método de generación de token
        Mockito.when(jwtUtil.generateToken(Mockito.any(User.class))).thenReturn("mocked-token");

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
        assertEquals("mocked-token", result.getToken());
        // Puedes agregar más verificaciones según tus requisitos
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
        signUpRequest.setPhones(Arrays.asList(new PhoneDTO(123456789l, 123, "US")));

        // Verifica que se lance una excepción cuando el usuario ya existe
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.signUp(signUpRequest);
        });

        // Verifica el mensaje de la excepción
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getRawStatusCode());
    }

    @Test
    void getUserInfoFromToken_UserNotFound() {
        // Simula el comportamiento del repositorio cuando el usuario no se encuentra
        Mockito.when(userRepository.getByToken(Mockito.anyString())).thenReturn(Optional.empty());

        // Configura el token
        String token = "test-token";

        // Verifica que se lance una excepción cuando el usuario no se encuentra
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUserInfoFromToken(token);
        });

        // Verifica el mensaje de la excepción
        assertEquals("Usuario no encontrado para el token proporcionado", exception.getMessage());
    }

}
