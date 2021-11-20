package com.isbd.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserDto {
    @Size(min = 4, message = "Username must contain at least 4 symbols")
    private final String username;

    @Size(min = 8, message = "Password must contain at least 8 symbols")
    private final String password;
}
