package com.rviewer.safebox.infrastructure.controllers;

import com.rviewer.safebox.application.request.AddItemRequest;
import com.rviewer.safebox.application.services.item.ItemAdder;
import com.rviewer.safebox.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Put controller for adding an item to a given safe box.
 */
@RestController
@Log4j2
@RequiredArgsConstructor
public class PutAddItemToSafeBoxController {

    private final ItemAdder itemAdder;
    private final SecurityUtils securityUtils;

    @PutMapping(value = "/safebox/{id}/items", consumes = "application/json")
    public ResponseEntity addItemToSafeBox(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @PathVariable String id,
            @RequestBody ItemList request
    ) {
        securityUtils.validateAuthorization(auth, id);
        var user = securityUtils.getRequestUser(auth);
        log.debug("Received request to add {} items to safe box with id: {}", request.items.size(), id);
        itemAdder.addItems(new AddItemRequest(id, user, request.items));
        return ResponseEntity.ok().build();
    }

    record ItemList(List<String> items) {}
}
