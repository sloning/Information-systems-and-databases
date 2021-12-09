package com.isbd.security;

import com.isbd.exception.WrongCredentialsException;
import com.isbd.security.jwt.JwtUser;
import com.isbd.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    private final AuthService authService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public long getPlayerId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JwtUser jwtUser = (JwtUser) auth.getPrincipal();
            if (authService.isPlayerExists(jwtUser.getId())) {
                return jwtUser.getId();
            }
        }
        throw new WrongCredentialsException("У вас не прав на просмотр данной информации");
    }
}
