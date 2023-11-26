package com.example.bciusermicroservice.controller;

import com.example.bciusermicroservice.dto.UserDTO;
import com.example.bciusermicroservice.model.ErrorResponse;
import com.example.bciusermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String token) {
        try {

            // Buscar el usuario en la base de datos por nombre de usuario
            Optional<UserDTO> user = userService.getUserInfoFromToken(token);

                if (user.isPresent()) {
                    // Devolver el usuario encontrado
                    UserDTO userDto = user.get();
                    // Devolver el usuario encontrado
                    return ResponseEntity.ok().body(userDto.toOrderedMap());
                } else {
                    // Manejar el caso en que el usuario no se encuentra
                    throw new UsernameNotFoundException("Usuario no encontrado");
                }
            } catch (UsernameNotFoundException e) {
                // Manejar la excepción y construir la respuesta de error
                ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                        Instant.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Usuario no encontrado"
                );

                ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(errorDetail));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            } catch (Exception e) {
                // Manejar otras excepciones y construir la respuesta de error
                ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                        Instant.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        e.getMessage()
                );

                ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(errorDetail));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }

        }

    @GetMapping("/")
    public String home() {
        return "Hello world!";
    }
}
