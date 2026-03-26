package com.batiot.springmcpproxy.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Validates the {@code X-Api-Key} header on every request.
 * Returns HTTP 401 if the header is missing or does not match the configured key.
 */
@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    static final String API_KEY_HEADER = "X-Api-Key";

    private final String expectedApiKey;

    public ApiKeyAuthFilter(@Value("${mcp.server.api-key}") String expectedApiKey) {
        this.expectedApiKey = expectedApiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String providedKey = request.getHeader(API_KEY_HEADER);
        if (!expectedApiKey.equals(providedKey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing API key");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
