package com.rviewer.safebox.infrastructure.controllers;

import com.rviewer.safebox.application.request.ListSafeBoxItemsRequest;
import com.rviewer.safebox.application.response.ListSafeBoxItemsResponse;
import com.rviewer.safebox.application.services.item.ItemFinder;
import com.rviewer.safebox.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


/**
 * Get controller for listing the items of a safe box.
 */
@RestController
@Log4j2
@RequiredArgsConstructor
public class GetListSafeBoxItemsController {

    private final ItemFinder itemFinder;
    private final SecurityUtils securityUtils;

    @GetMapping(value = "/safebox/{id}/items", produces = "application/json")
    public ResponseEntity<ListSafeBoxItemsResponse> getSafeBoxItems(
            @RequestHeader (HttpHeaders.AUTHORIZATION) String auth,
            @PathVariable String id
    ) {
        securityUtils.validateAuthorization(auth, id);
        var user = securityUtils.getRequestUser(auth);
        log.debug("Received request to list the items of safebox {} by user {}", id, user);
        var response = itemFinder.items(new ListSafeBoxItemsRequest(user,UUID.fromString(id)));
        return ResponseEntity.ok(response);
    }
}
