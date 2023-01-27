package com.rviewer.safebox.infrastructure.config;

import com.rviewer.safebox.application.services.item.ItemAdder;
import com.rviewer.safebox.application.services.SafeBoxCreator;
import com.rviewer.safebox.application.services.item.ItemFinder;
import com.rviewer.safebox.application.services.security.SecurityService;
import com.rviewer.safebox.domain.SafeBoxRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dependency injection configuration.
 */
@Configuration
public class DependencyInjectionConf {

    //* Bean to inject the SafeBoxCreator service.
    @Bean
    public SafeBoxCreator safeBoxCreator(SafeBoxRepository safeBoxRepository) { return new SafeBoxCreator(safeBoxRepository); }

    //* Bean to inject the ItemAdder service.
    @Bean
    public ItemFinder itemFinder(SafeBoxRepository safeBoxRepository) { return new ItemFinder(safeBoxRepository); }

    //* Bean to inject the ItemAdder service.
    @Bean
    public ItemAdder itemAdder(SafeBoxRepository safeBoxRepository) { return new ItemAdder(safeBoxRepository); }

    //* Bean to inject the SecurityService service.
    @Bean
    public SecurityService securityService(SafeBoxRepository safeBoxRepository) { return new SecurityService(safeBoxRepository); }
}

