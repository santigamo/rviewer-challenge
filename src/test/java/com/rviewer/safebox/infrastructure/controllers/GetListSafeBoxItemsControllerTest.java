package com.rviewer.safebox.infrastructure.controllers;

import com.rviewer.safebox.application.services.item.ItemFinder;
import com.rviewer.safebox.domain.SafeBoxRepository;
import com.rviewer.safebox.infrastructure.security.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class GetListSafeBoxItemsControllerTest {

    private SafeBoxRepository safeBoxRepository;
    private GetListSafeBoxItemsController controller;

    @BeforeEach
    void setUp() {
        safeBoxRepository = mock(SafeBoxRepository.class);
        ItemFinder itemFinder = new ItemFinder(safeBoxRepository);

        SecurityUtils securityUtils = mock(SecurityUtils.class);
        when(securityUtils.validateAuthentication(any())).thenReturn(true);
        when(securityUtils.getRequestUser(any())).thenReturn("user");

        controller = new GetListSafeBoxItemsController(itemFinder, securityUtils);
    }

    @Test
    void list_safebox_content() throws Exception {
        // Given
        var testItems = List.of("item1", "item2");
        when(safeBoxRepository.findItemsBy(any())).thenReturn(testItems);
        // When
        var response = controller.getSafeBoxItems("authentication", "1bb1a31d-b525-4ee3-b4c3-5e8fe49c1af5");

        // Then
        assertEquals(testItems, response.getBody().items());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void list_empty_safebox() {
        // Given
        when(safeBoxRepository.findItemsBy(any())).thenReturn(new ArrayList<>());

        // When
        var response = controller.getSafeBoxItems("authentication", "1bb1a31d-b525-4ee3-b4c3-5e8fe49c1af5");

        // Then
        assertEquals(new ArrayList<>(), response.getBody().items());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}