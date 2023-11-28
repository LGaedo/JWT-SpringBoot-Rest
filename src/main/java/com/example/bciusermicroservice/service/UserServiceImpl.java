package com.example.bciusermicroservice.service;

import com.example.bciusermicroservice.dto.PhoneDTO;
import com.example.bciusermicroservice.dto.SignUpRequest;
import com.example.bciusermicroservice.dto.UserDTO;
import com.example.bciusermicroservice.exception.SignUpException;
import com.example.bciusermicroservice.model.Phone;
import com.example.bciusermicroservice.model.User;
import com.example.bciusermicroservice.repository.UserRepository;
import com.example.bciusermicroservice.security.JwtUtil;
import com.example.bciusermicroservice.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .ifPresent(existingUser -> {throw new SignUpException("User already exists");});

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format");
        }
    }

    private void validatePassword(String password) {
        if (!ValidationUtils.isValidPasswordFormat(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password format");
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
        userDto.setToken(jwtUtil.generateToken(user.getUsername()));
        userDto.setActive(user.isActive());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String token) {

        User userTmp = new User();
        String username = jwtUtil.extractUsername(token);

        if(jwtUtil.validateJwtToken(token, username)) {
            userTmp = userRepository.getByEmail(username);
        }

        Optional<User> optionalUser = Optional.ofNullable(userTmp);

        return optionalUser.map(user -> {
            UserDTO userDTO = convertToDto(user);

            return Optional.of(userDTO);
        }).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado para el token proporcionado"));
    }

    private UserDTO convertToDto(User user) {
        // Mapeo de User a UserDto
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setCreated(user.getCreated());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setToken(jwtUtil.generateToken(user.getUsername()));
        userDto.setActive(user.isActive());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

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
