package com.example.bciusermicroservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PhoneDTOTest {

    @Test
    public void testPhoneDTOConstructor() {
        // Arrange
        Long number = 123456789L;
        int cityCode = 7;
        String countryCode = "25";

        // Act
        PhoneDTO phoneDTO = new PhoneDTO(number, cityCode, countryCode);

        // Assert
        assertEquals(number, phoneDTO.getNumber());
        assertEquals(cityCode, phoneDTO.getCityCode());
        assertEquals(countryCode, phoneDTO.getCountrycode());
    }

    @Test
    public void testPhoneDTODefaultConstructor() {
        // Arrange
        PhoneDTO phoneDTO = new PhoneDTO();

        // Assert (default values)
        assertNull(phoneDTO.getNumber());
        assertEquals(0, phoneDTO.getCityCode());
        assertNull(phoneDTO.getCountrycode());
    }
}

