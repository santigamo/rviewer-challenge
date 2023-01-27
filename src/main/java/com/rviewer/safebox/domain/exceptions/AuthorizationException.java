package com.rviewer.safebox.domain.exceptions;

public class AuthorizationException extends Exception{
    public AuthorizationException() {
        super("Specified Basic Auth does not match");
    }
}
