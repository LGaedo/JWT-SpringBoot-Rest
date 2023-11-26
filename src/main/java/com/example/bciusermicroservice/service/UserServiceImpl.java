package com.example.bciusermicroservice.service;

import com.example.bciusermicroservice.util.ValidationUtils;
import com.example.bciusermicroservice.dto.PhoneDTO;
import com.example.bciusermicroservice.dto.SignUpRequest;
import com.example.bciusermicroservice.dto.UserDTO;
import com.example.bciusermicroservice.model.ErrorResponse;
import com.example.bciusermicroservice.model.Phone;
import com.example.bciusermicroservice.model.User;
import com.example.bciusermicroservice.repository.UserRepository;
import com.example.bciusermicroservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO signUp(SignUpRequest signUpRequest) {
        validateEmail(signUpRequest.getEmail());
        validatePassword(signUpRequest.getPassword());

        userRepository.findByEmail(signUpRequest.getEmail())
                .ifPresent(existingUser -> {
                    ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                            Instant.now(),
                            HttpStatus.BAD_REQUEST.value(),
                            "User already exists"
                    );
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorDetail.toString());
                });

        User user = createUserFromRequest(signUpRequest);
        userRepository.save(user);

        return createSuccessResponse(user);
    }

    private User createUserFromRequest(SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken(jwtUtil.generateToken(user));
        user.setActive(true);

        // Lógica para los teléfonos
        List<Phone> phones = new ArrayList<>();
        if (signUpRequest.getPhones() != null) {
            phones.addAll(signUpRequest.getPhones().stream()
                    .map(phoneDTO -> {
                        Phone phone = new Phone();
                        phone.setNumber(phoneDTO.getNumber());
                        phone.setCitycode(phoneDTO.getCityCode());
                        phone.setCountrycode(phoneDTO.getCountrycode());
                        phone.setUser(user);
                        return phone;
                    })
                    .collect(Collectors.toList()));
        }
        user.setPhones(phones);

        return user;
    }

    private void validateEmail(String email) {
        if (!ValidationUtils.isValidEmailFormat(email)) {
            ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                    Instant.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid email format"
            );
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorDetail.toString());
        }
    }

    private void validatePassword(String password) {
        if (!ValidationUtils.isValidPasswordFormat(password)) {
            ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                    Instant.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid password format"
            );
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorDetail.toString());
        }
    }

    private UserDTO createSuccessResponse(User user) {
        List<PhoneDTO> phoneDTOs = user.getPhones().stream()
                .map(phone -> new PhoneDTO(phone.getNumber(), phone.getCitycode(), phone.getCountrycode()))
                .collect(Collectors.toList());

        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setCreated(user.getCreated());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setToken(user.getToken());
        userDto.setActive(user.isActive());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    @Override
    public Optional<UserDTO> getUserInfoFromToken(String token) {
        // Recuperar el usuario desde la base de datos usando el nombre de usuario
        Optional<User> optionalUser = userRepository.getByToken(token);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado para el token proporcionado"));

        if (user == null) {
            // Manejar el caso en el que el usuario no se encuentre en la base de datos
            throw new UsernameNotFoundException("Usuario no encontrado para el token proporcionado");
        }

        //convert to dto
        UserDTO userDTO = convertToDto(user);


        // Actualizar el token (si es necesario) antes de devolver la información del usuario
        String updatedToken = jwtUtil.generateToken(user);
        user.setToken(updatedToken);

        // Guardar los cambios en el usuario (actualizar el token)
        userRepository.save(user);

        return Optional.ofNullable(userDTO);
    }

    private UserDTO convertToDto(User user) {
        // Mapeo de User a UserDto
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setCreated(user.getCreated());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setToken(user.getToken());
        userDto.setActive(user.isActive());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());  // Asegúrate de que estás manejando correctamente las contraseñas en tu aplicación (no deberías devolver la contraseña)

        // Mapeo de phones
        List<PhoneDTO> phoneDtos = user.getPhones().stream()
                .map(phone -> {
                    PhoneDTO phoneDto = new PhoneDTO();
                    phoneDto.setNumber(phone.getNumber());
                    phoneDto.setCityCode(phone.getCitycode());
                    phoneDto.setCountrycode(phone.getCountrycode());
                    return phoneDto;
                })
                .collect(Collectors.toList());

        userDto.setPhones(phoneDtos);

        return userDto;
    }

}
