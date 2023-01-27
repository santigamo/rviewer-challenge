package com.rviewer.safebox.application.request;

import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * Class to represent a request to list the content of the given safebox.
 * @param name The name of the safe box.
 * @param id The id of the safe box.
 */
public record ListSafeBoxItemsRequest(@NonNull String name, @NonNull UUID id) {}
