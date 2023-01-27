package com.rviewer.safebox.application.response;

import lombok.NonNull;

import java.util.UUID;

/**
 * Class to represent a safebox creation successful response.
 * @param id The id of the safebox created.
 */
public record CreateSafeBoxResponse(@NonNull UUID id) {}
