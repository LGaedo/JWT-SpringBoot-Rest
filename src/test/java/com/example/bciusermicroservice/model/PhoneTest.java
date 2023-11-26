package com.example.bciusermicroservice.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PhoneTest {

    @Test
    public void testPhoneEntity() {
        // Crear una instancia de User
        User user = new User();
        user.setId(1L);
        user.setName("NombreUsuario");
        user.setEmail("usuario@example.com");
        user.setPassword("password123");

        // Crear una instancia de Phone
        Phone phone = new Phone();
        phone.setId(1L);
        phone.setNumber(123456789L);
        phone.setCitycode(123);
        phone.setCountrycode("US");

        // Establecer la relación entre Phone y User
        phone.setUser(user);

        // Añadir el teléfono a la lista de teléfonos del usuario
        user.setPhones(Arrays.asList(phone));

        // Verificar que los campos se hayan establecido correctamente
        assertNotNull(phone.getUser());
        assertEquals(user, phone.getUser());
        assertEquals(1L, phone.getId());
        assertEquals(123456789L, phone.getNumber());
        assertEquals(123, phone.getCitycode());
        assertEquals("US", phone.getCountrycode());
    }
}
