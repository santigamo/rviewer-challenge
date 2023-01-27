package com.rviewer.safebox.infrastructure.security;

import com.rviewer.safebox.application.services.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for security concerns.
 */
@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final SecurityService securityService;

    /**
     * Encodes the given string using Base64.
     * @param password The string to hashString.
     * @return The encoded string.
     */
    public static String hashString(String password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashbytes = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashbytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String getRequestUser(String auth) {
        final String[] values = retrieveCredentials(auth);
        return values[0];
    }

    public boolean validateAuthentication(String auth) {
        if (auth.length() < 6)
            return false;
        final String[] credentials = retrieveCredentials(auth);

        var user = credentials[0];
        var password = hashString(credentials[1]);
        return securityService.validateAuthentication(user, password);
    }

    public void validateAuthorization(String auth, String id) {
        final String[] credentials = retrieveCredentials(auth);

        var user = credentials[0];
        securityService.validateAuthorization(user, id);
    }

    private static String[] retrieveCredentials(String auth) {
        String base64Credentials = auth.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":", 2);
    }
}
