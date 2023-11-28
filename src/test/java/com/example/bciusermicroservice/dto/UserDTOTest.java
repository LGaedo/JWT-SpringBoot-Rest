package com.example.bciusermicroservice.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
    void toOrderedMap() {
        // Crear un objeto UserDTO para la prueba
        UserDTO userDTO = createValidUserDTO();

        // Crear un mock para la lista de teléfonos
        List<PhoneDTO> mockedPhones = Arrays.asList(
                new PhoneDTO(123456789L, 7, "25"),
                new PhoneDTO(987654321L, 5, "10")
        );
        userDTO.setPhones(mockedPhones);

        // Llamar al método que se va a probar
        Map<String, Object> orderedMap = userDTO.toOrderedMap();

        // Verificar que el mapa generado tenga las claves y valores esperados
        assertEquals(1L, orderedMap.get("id"));

        // Verificar que la representación de la lista sea la esperada
        List<PhoneDTO> expectedPhones = Arrays.asList(
                new PhoneDTO(123456789L, 7, "25"),
                new PhoneDTO(987654321L, 5, "10")
        );
        assertEquals(expectedPhones, orderedMap.get("phones"));
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

