package com.isbd.service.auth;

import com.isbd.DTO.UserDTO;
import com.isbd.exception.ValidationException;

public class UserValidator {
    public static void validateUser(UserDTO userDTO) {
        validateUsername(userDTO.getUsername());
        validatePassword(userDTO.getPassword());
    }

    private static void validateUsername(String username) {
        if (username == null || username.trim().length() < 4)
            throw new ValidationException("Username must contain at least 4 symbols");
    }

    private static void validatePassword(String password) {
        if (password == null || password.trim().length() < 8)
            throw new ValidationException("Password must contain at least 8 symbols");
    }
}
