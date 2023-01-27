package com.rviewer.safebox.domain;

import java.util.List;

/**
 * SafeBoxRepository interface.
 */
public interface SafeBoxRepository {
    void save(SafeBox safeBox);
    List<String> findItemsBy(String id);
    void addItemsTo(String id, List<String> items);
    boolean checkAuthentication(String name, String password);
    void checkAuthorization(String name, String id);
}
