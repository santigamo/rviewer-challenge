package com.rviewer.safebox.infrastructure.security;

import com.rviewer.safebox.application.services.security.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityUtilsTest {

    private SecurityService securityService;

    private SecurityUtils securityUtils;

    @BeforeEach
    void setUp() {
        securityService = mock(SecurityService.class);
        securityUtils = new SecurityUtils(securityService);
    }
    @Test
    void should_hash_a_String_successfully() {
        var hash = SecurityUtils.hashString("password");
        assertEquals("c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484", hash);
    }

    @Test
    void should_get_request_user() {
        // Given
        String auth = "Basic dGVzdC11c2VyOg==";

        // When
        var actualUser = securityUtils.getRequestUser(auth);

        // Then
        assertEquals("test-user", actualUser);
    }

    @Test
    void should_validate_authentication_ok() {
        // Given
        String auth = "Basic dGVzdC11c2VyOjEyMzQ=";
        when(securityService.validateAuthentication(any(), any())).thenReturn(true);
        // When
        var result = securityUtils.validateAuthentication(auth);

        // Then
        assertTrue(result);
    }

    @Test
    void should_validate_authorization_ok() {
        // Given
        String auth = "Basic dGVzdC11c2VyOjEyMzQ=";

        // Then
        assertDoesNotThrow(() -> securityUtils.validateAuthorization(auth, "id"));
    }
}