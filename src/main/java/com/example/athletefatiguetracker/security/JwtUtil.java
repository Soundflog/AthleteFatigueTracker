package com.example.athletefatiguetracker.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    /**
     * Возвращает athleteId из токена (предполагается, что поле "sub" = userId).
     */
    public Long getAthleteId(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String sub = jwt.getSubject(); // например, "123"
        return Long.parseLong(sub);
    }
}