package com.isbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class LoginDto {
    @Size(min = 4, message = "Имя пользователя должно содержать как минимум 4 символа")
    private final String username;

    @Size(min = 8, message = "Пароль должен содержать как минимум 8 символов")
    private final String password;
}
