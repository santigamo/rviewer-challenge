package com.rviewer.safebox.application.response;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Class to represent the items of a safe box.
 * @param items The list of items.
 */
public record ListSafeBoxItemsResponse(@NonNull List<String> items) {}
