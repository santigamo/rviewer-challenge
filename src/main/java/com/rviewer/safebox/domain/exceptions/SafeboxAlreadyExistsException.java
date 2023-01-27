package com.rviewer.safebox.domain.exceptions;

public class SafeboxAlreadyExistsException extends InvalidArgumentSafeboxException {
    public SafeboxAlreadyExistsException() {
        super("Safebox already exists");
    }
}
