package com.rviewer.safebox.domain;

import com.rviewer.safebox.domain.exceptions.InvalidSafeboxNameException;
import com.rviewer.safebox.domain.exceptions.InvalidSafeboxPasswordException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
public class SafeBox {

    private final SafeBoxId id;

    @Getter
    private String name;

    @Getter
    private final String password;

    @Getter
    private final List<String> items;

    @Value(staticConstructor = "of")
    public static class SafeBoxId {
        @NonNull UUID uuid;
    }

    public SafeBox(String name, String password) {
        this(SafeBoxId.of(UUID.randomUUID()), name, password, new ArrayList<>());
    }

    SafeBox(@NonNull SafeBoxId id, @NonNull String name, @NonNull String password, @NonNull List<String> items) {
        this.id = id;
        setName(name);
        this.password = password;
        this.items = new ArrayList<>(items);
    }

    /**
     * Returns the {@link UUID} of this safebox.
     *
     * @return safebox id
     */
    public SafeBoxId getId() {
        return id;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new InvalidSafeboxNameException(name);

        this.name = name;
    }
}
