package com.examples.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestHash {
    public static void main(String[] args) {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin";

        String hashed = encoder.encode(rawPassword);
        System.out.println("Hash generado: " + hashed);

        boolean match = encoder.matches(rawPassword, hashed);
        System.out.println("¿Coincide?: " + match);
    }
}

