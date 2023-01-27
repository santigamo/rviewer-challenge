package com.rviewer.safebox.application.request;

import lombok.NonNull;

/**
 * Class to represent a request to create a safe box.
 * @param name The name of the safe box.
 * @param password The password of the safe box.
 */
public record CreateSafeBoxRequest(@NonNull String name,@NonNull String password) { }
