package com.rviewer.safebox.infrastructure.config;

import com.rviewer.safebox.domain.SafeBoxRepository;
import com.rviewer.safebox.infrastructure.persistence.PostgreSafeboxRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Database configuration.
 */
@Configuration
public class DatabaseConfig {

    @Bean
    SafeBoxRepository safeBoxRepository(NamedParameterJdbcTemplate jdbcTemplate) { return new PostgreSafeboxRepository(jdbcTemplate); }
}
