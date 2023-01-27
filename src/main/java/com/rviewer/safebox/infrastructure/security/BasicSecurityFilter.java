package com.rviewer.safebox.infrastructure.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Basic security filter.
 */
@Component
@Order(1)
@Log4j2
public class BasicSecurityFilter implements Filter {

    @Autowired
    SecurityUtils securityUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (isPublicURI(req)) {
            chain.doFilter(request, response);
            return;
        }

        var auth = req.getHeader("Authorization");
        if (auth == null || auth.isEmpty()) {
            log.error("Authorization header is missing");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = res.getWriter();
            writer.println("HTTP Status 401 - " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return;
        } else if (!securityUtils.validateAuthentication(auth)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = res.getWriter();
            writer.println("HTTP Status 401 - " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return;
        }


        chain.doFilter(request, response);
    }

    /**
     * Checks if the given URI is public.
     * @param req The {@link HttpServletRequest} to check.
     * @return true if the URI is public, false otherwise.
     */
    private boolean isPublicURI(HttpServletRequest req) {
        return req.getRequestURI().startsWith("/actuator/")
                || req.getRequestURI().startsWith("favicon")
                || req.getRequestURI().equals("/safebox");
    }
}
