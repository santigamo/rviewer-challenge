package com.rviewer.safebox.application.services;

import com.rviewer.safebox.application.request.CreateSafeBoxRequest;
import com.rviewer.safebox.application.response.CreateSafeBoxResponse;
import com.rviewer.safebox.domain.SafeBox;
import com.rviewer.safebox.domain.SafeBoxRepository;
import com.rviewer.safebox.domain.exceptions.InvalidSafeboxPasswordException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static com.rviewer.safebox.infrastructure.security.SecurityUtils.hashString;

/**
 * Service class for creating a safe box.
 */
@AllArgsConstructor
@Log4j2
public class SafeBoxCreator {

    private static final String PASSWORD_STRENGTH = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";

    private SafeBoxRepository safeBoxRepository;

    public CreateSafeBoxResponse create(CreateSafeBoxRequest request) {
        if (!request.password().matches(PASSWORD_STRENGTH)) {
            log.error("Password doesn't meet the requirements");
            throw new InvalidSafeboxPasswordException();
        }

        var safeBox = new SafeBox(request.name(), hashString(request.password()));
        safeBoxRepository.save(safeBox);
        return new CreateSafeBoxResponse(safeBox.getId().getUuid());
    }
}
