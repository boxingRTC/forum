package com.esliceu.forum.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Encoder {
    public static String encode(String str) {
        try {
            // Crear una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Obtener el arreglo de bytes del string
            byte[] encoded = digest.digest(str.getBytes());
            // Convertir el arreglo de bytes a una representación en hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : encoded) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejo de excepción en caso de que el algoritmo no sea soportado
            e.printStackTrace();
            return null;
        }
    }
}
