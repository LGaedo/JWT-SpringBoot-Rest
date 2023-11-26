package com.example.bciusermicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private List<ErrorDetail> errors;

    @Data
    @AllArgsConstructor
    public static class ErrorDetail {
        private Instant timestamp;
        private int codigo;
        private String detail;
    }
}

