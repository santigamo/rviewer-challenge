package com.rviewer.safebox.domain.exceptions;

public class InvalidSafeboxPasswordException extends InvalidArgumentSafeboxException {
    public InvalidSafeboxPasswordException() {
        super("Invalid password provided; The password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit and one special character.");
    }
}
