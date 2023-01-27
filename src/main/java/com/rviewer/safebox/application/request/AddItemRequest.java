package com.rviewer.safebox.application.request;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Class to represent a request to add items to the given safe box.
 * @param id The id of the safe box.
 * @param name The name of the safe box.
 * @param items The list of items to add.
 */
public record AddItemRequest(@NonNull String id, @NonNull String name, @NonNull List<String> items) {}
