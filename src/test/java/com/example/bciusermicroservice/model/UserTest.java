package com.example.bciusermicroservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserEntity() {
        // Crear un usuario de ejemplo
        User user = new User();
        user.setId(1L);
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
//        user.setToken("exampleToken");
        user.setActive(true);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");

        // Crear un teléfono de ejemplo
        Phone phone = new Phone();
        phone.setId(1L);
        phone.setNumber(123456789L);
        phone.setCitycode(123);
        phone.setCountrycode("US");

        // Establecer la relación entre el usuario y el teléfono
        user.setPhones(Arrays.asList(phone));
        phone.setUser(user);

        // Verificar que los campos se establecieron correctamente
        assertEquals(1L, user.getId());
        assertNotNull(user.getCreated());
        assertNotNull(user.getLastLogin());
        assertTrue(user.isActive());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password", user.getPassword());

        // Verificar la relación con el teléfono
        assertNotNull(user.getPhones());
        assertEquals(1, user.getPhones().size());
        assertEquals(phone, user.getPhones().get(0));
        assertEquals(user, phone.getUser());
    }
}
