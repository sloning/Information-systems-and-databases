package com.isbd.security;

public interface SecurityConstants {
    String SECRET = System.getenv("SECURITY_SECRET");
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    long EXPIRATION_TIME = 864000000; // milliseconds = 10 days
}
