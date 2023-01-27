package com.rviewer.safebox.application.services.security;

import com.rviewer.safebox.domain.SafeBoxRepository;
import lombok.AllArgsConstructor;

/**
 * Service class for security concerns.
 */
@AllArgsConstructor
public class SecurityService {

    private SafeBoxRepository safeBoxRepository;

    public boolean validateAuthentication(String name, String password) {
        return safeBoxRepository.checkAuthentication(name, password);
    }

    public void validateAuthorization(String name, String id) {
        safeBoxRepository.checkAuthorization(name, id);
    }
}
