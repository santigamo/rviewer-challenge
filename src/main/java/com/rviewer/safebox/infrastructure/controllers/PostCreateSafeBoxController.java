package com.rviewer.safebox.infrastructure.controllers;

import com.rviewer.safebox.application.request.CreateSafeBoxRequest;
import com.rviewer.safebox.application.response.CreateSafeBoxResponse;
import com.rviewer.safebox.application.services.SafeBoxCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Post controller for creating a safe box.
 */
@RestController
@Log4j2
@RequiredArgsConstructor
public class PostCreateSafeBoxController {

    private final SafeBoxCreator safeBoxCreator;

    @PostMapping("/safebox")
    public ResponseEntity<CreateSafeBoxResponse> postCreateSafeBox(
            @RequestBody CreateSafeBoxRequest request
    ) {
        log.debug("Received request to create the safe box: {}", request.name());
        var response = safeBoxCreator.create(request);
        return ResponseEntity.ok(response);
    }
}
