package com.example.bciusermicroservice.model;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTest {

    @Test
    public void testErrorResponse() {
        // Crear un ErrorDetail de ejemplo
        ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                Instant.now(),
                404,
                "Recurso no encontrado");

        // Crear una lista con el ErrorDetail
        List<ErrorResponse.ErrorDetail> errorDetails = Arrays.asList(errorDetail);

        // Crear una instancia de ErrorResponse con la lista de ErrorDetail
        ErrorResponse errorResponse = new ErrorResponse(errorDetails);

        // Verificar que la lista de errores coincida
        assertEquals(errorDetails, errorResponse.getErrors());
    }
}
