package com.example.bciusermicroservice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static boolean isValidEmailFormat(String email) {
        // Patrón de expresión regular para validar el formato del correo electrónico
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPasswordFormat(String password) {
        // Expresión regular para validar el formato de la contraseña
        String regex = "^(?=.*[A-ZÑ])(?=.*\\d.*\\d)[a-zA-ZñÑ\\d]{8,12}$";
        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(regex);
        // Crear un objeto Matcher para la cadena de entrada
        Matcher matcher = pattern.matcher(password);
        // Verificar si la contraseña cumple con el formato requerido
        return matcher.matches();
    }
}
