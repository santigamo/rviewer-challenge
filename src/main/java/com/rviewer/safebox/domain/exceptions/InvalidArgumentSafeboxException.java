package com.rviewer.safebox.domain.exceptions;

public class InvalidArgumentSafeboxException extends IllegalArgumentException{
    public InvalidArgumentSafeboxException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidArgumentSafeboxException(String message) {
        super(message);
    }
}
