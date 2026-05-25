package com.ingjcfv.rendicash11.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Encriptador {
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_BYTES = 16;
    private static final int HASH_BYTES = 32;   // 256 bits
    private static final int ITERATIONS = 40000; // Ajusta según rendimiento (≥ 10000 recomendado)

    public Encriptador() {
    }

    /**
     * Genera un hash seguro a partir de la contraseña en texto claro.
     * Retorna un string con el formato: "iterations:salt:hash" todo en Base64.
     */
    public static String hashPassword(String plainPassword) throws Exception {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }

        // Generar salt aleatorio
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);

        // Derivar hash
        byte[] hash = pbkdf2(plainPassword.toCharArray(), salt, ITERATIONS, HASH_BYTES);

        // Codificar en Base64
        String saltB64 = Base64.encodeToString(salt, Base64.NO_WRAP);
        String hashB64 = Base64.encodeToString(hash, Base64.NO_WRAP);

        // Guardar junto con las iteraciones (para poder usar diferentes iteraciones en el futuro)
        return ITERATIONS + ":" + saltB64 + ":" + hashB64;
    }

    /**
     * Verifica si la contraseña ingresada coincide con el hash almacenado.
     */
    public static boolean verifyPassword(String plainPassword, String storedHash) throws Exception {
        if (plainPassword == null || storedHash == null) {
            return false;
        }

        String[] parts = storedHash.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Formato de hash inválido");
        }

        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = Base64.decode(parts[1], Base64.NO_WRAP);
        byte[] hashFromStorage = Base64.decode(parts[2], Base64.NO_WRAP);

        // Calcular hash de la contraseña ingresada con el mismo salt e iteraciones
        byte[] testHash = pbkdf2(plainPassword.toCharArray(), salt, iterations, hashFromStorage.length);

        // Comparación en tiempo constante (importante para evitar timing attacks)
        return MessageDigest.isEqual(hashFromStorage, testHash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        SecretKey key = skf.generateSecret(spec);
        return key.getEncoded();
    }
}
