package com.rviewer.safebox.infrastructure.persistence;

import com.rviewer.safebox.domain.SafeBox;
import com.rviewer.safebox.domain.exceptions.SafeboxAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostgreSafeboxRepositoryTest {

    NamedParameterJdbcTemplate jdbcTemplate;

    PostgreSafeboxRepository repository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        repository = new PostgreSafeboxRepository(jdbcTemplate);
    }

    @Test
    void should_save_safebox_succesfully() {
        // Given
        var safebox = new SafeBox("safeboxname", "password");
        when(jdbcTemplate.update(anyString(), (SqlParameterSource) any())).thenReturn(1);

        // When & Then
        Assertions.assertDoesNotThrow(() -> repository.save(safebox));
    }

    @Test
    void should_throw_already_exists_exception() {
        // Given
        var safebox = new SafeBox("safeboxname", "password");
        when(jdbcTemplate.update(anyString(), (SqlParameterSource) any())).thenThrow(new SafeboxAlreadyExistsException());

        // When & Then
        Assertions.assertThrows(SafeboxAlreadyExistsException.class, () -> repository.save(safebox));
    }
}