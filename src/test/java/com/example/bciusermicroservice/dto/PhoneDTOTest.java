package com.example.bciusermicroservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.example.bciusermicroservice.dto.PhoneDTO;

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
        assertEquals(null, phoneDTO.getNumber());
        assertEquals(0, phoneDTO.getCityCode());
        assertEquals(null, phoneDTO.getCountrycode());
    }
}

