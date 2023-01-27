package com.rviewer.safebox.application.services.item;

import com.rviewer.safebox.application.request.ListSafeBoxItemsRequest;
import com.rviewer.safebox.application.response.ListSafeBoxItemsResponse;
import com.rviewer.safebox.domain.SafeBoxRepository;
import lombok.AllArgsConstructor;

/**
 * Service class for find items in safeboxes
 */
@AllArgsConstructor
public class ItemFinder {

    private SafeBoxRepository safeBoxRepository;

    public ListSafeBoxItemsResponse items(ListSafeBoxItemsRequest request) {
        var safeBox = safeBoxRepository.findItemsBy(request.id().toString());
        return new ListSafeBoxItemsResponse(safeBox);
    }
}
