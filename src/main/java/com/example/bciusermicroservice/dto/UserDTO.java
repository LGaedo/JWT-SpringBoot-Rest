package com.example.bciusermicroservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserDTO {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
    private String name;
    private String email;
    private List<PhoneDTO> phones;
    private String password;

    // Método para ordenar los campos antes de convertir a JSON
    public Map<String, Object> toOrderedMap() {
        Map<String, Object> orderedMap = new LinkedHashMap<>();
        orderedMap.put("id", this.id);
        orderedMap.put("created", this.created);
        orderedMap.put("lastLogin", this.lastLogin);
        orderedMap.put("token", this.token);
        orderedMap.put("isActive", this.isActive);
        orderedMap.put("name", this.name);
        orderedMap.put("email", this.email);
        orderedMap.put("password", this.password);
        orderedMap.put("phones", this.phones);
        return orderedMap;
    }
}
