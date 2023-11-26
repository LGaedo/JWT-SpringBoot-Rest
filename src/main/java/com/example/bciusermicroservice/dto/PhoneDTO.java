package com.example.bciusermicroservice.dto;

import lombok.Data;

@Data
public class PhoneDTO {
    private Long id;
    private Long number;
    private int cityCode;
    private String countrycode;

    public PhoneDTO(){};
    // Constructor con argumentos
    public PhoneDTO(Long number, int cityCode, String countrycode) {
        this.number = number;
        this.cityCode = cityCode;
        this.countrycode = countrycode;
    }
}
