package com.example.bciusermicroservice.controller;

import com.example.bciusermicroservice.dto.SignUpRequest;
import com.example.bciusermicroservice.dto.UserDTO;
import com.example.bciusermicroservice.security.JwtUtil;
import com.example.bciusermicroservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    public void signUp_ValidRequest_ReturnsCreated() throws Exception {
        // Mocking the service response
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setToken("mocked-token");
        given(userService.signUp(any(SignUpRequest.class))).willReturn(mockUserDTO);

        // Mocking the JWT extraction
        given(jwtUtil.extractUsername("mocked-token")).willReturn("mocked-user-email");

        // Creating a valid request
        SignUpRequest validRequest = new SignUpRequest();
        validRequest.setEmail("test@example.com");
        validRequest.setPassword("testPasswo78");

        // Performing the request and validating the response
        mockMvc.perform(MockMvcRequestBuilders.post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"password\":\"testPassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("mocked-token"));
    }

}

