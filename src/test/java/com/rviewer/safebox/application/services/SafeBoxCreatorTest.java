package com.rviewer.safebox.application.services;

import com.rviewer.safebox.application.request.CreateSafeBoxRequest;
import com.rviewer.safebox.domain.SafeBox;
import com.rviewer.safebox.domain.SafeBoxRepository;
import com.rviewer.safebox.domain.exceptions.InvalidSafeboxNameException;
import com.rviewer.safebox.domain.exceptions.InvalidSafeboxPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SafeBoxCreatorTest {

    private SafeBoxRepository safeBoxRepository;
    private SafeBoxCreator safeBoxCreator;

    @BeforeEach
    void setUp() {
        safeBoxRepository = mock(SafeBoxRepository.class);
        safeBoxCreator = new SafeBoxCreator(safeBoxRepository);
    }

    @Test
    void should_create_a_safebox_successfully() {
        // Given
        var name = "testName";
        var password = "GoodPassword0!";
        // When
        safeBoxCreator.create(new CreateSafeBoxRequest(name, password));

        // Then
        thenTheSafeBoxShouldBeSaved();
    }

    @Test
    void should_return_the_safebox_id() {
        // Given
        var name = "testName";
        var password = "GoodPassword0!";
        // When
        var response = safeBoxCreator.create(new CreateSafeBoxRequest(name, password));

        // Then
        assertNotNull(response.id());
    }
    @Test
    void should_throw_exception_when_password_is_not_strong_enough() {
        // Given
        var name = "testName";
        var password = "invalidPassword";
        // When
        assertThrows(InvalidSafeboxPasswordException.class, () -> safeBoxCreator.create(new CreateSafeBoxRequest(name, password)));
    }

    @Test
    void should_thrown_exception_when_name_is_blank() {
        // Given
        var name = "";
        var password = "GoodPassword0!";

        // When
        assertThrows(InvalidSafeboxNameException.class, () -> safeBoxCreator.create(new CreateSafeBoxRequest(name, password)));
    }

    private void thenTheSafeBoxShouldBeSaved() {
        verify(safeBoxRepository).save(
                any(SafeBox.class)
        );
    }
}