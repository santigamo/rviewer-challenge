package com.rviewer.safebox.application.services.item;

import com.rviewer.safebox.application.request.AddItemRequest;
import com.rviewer.safebox.domain.SafeBoxRepository;
import lombok.AllArgsConstructor;

/**
 * Service class to add items into a safebox.
 */
@AllArgsConstructor
public class ItemAdder {

    private SafeBoxRepository safeBoxRepository;

    public void addItems(AddItemRequest request) {
        safeBoxRepository.addItemsTo(request.id(), request.items());
    }
}
