package com.isbd.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.isbd.exception.JwtAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.isbd.config.AuthenticationConfigConstants.*;

@Component
public class JwtTokenProvider {
    private final Algorithm algorithm;

    public JwtTokenProvider() {
        algorithm = Algorithm.HMAC256(SECRET);
    }

    public String createToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withIssuer("isbd")
                .sign(algorithm);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("isbd")
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    public String getPlayerId(String token) {
        return JWT.decode(token).getSubject();
    }

    public Authentication getAuthentication(String token) {
        JwtUser userDetails = JwtUserFactory.create(getPlayerId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
