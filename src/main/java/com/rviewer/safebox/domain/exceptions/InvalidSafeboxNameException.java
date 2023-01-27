package com.rviewer.safebox.domain.exceptions;

public class InvalidSafeboxNameException extends InvalidArgumentSafeboxException {
    public InvalidSafeboxNameException(String name) {
        super("The name " + name + " is not a valid name");
    }
}
