package com.isbd.service.auth;

import com.isbd.dto.LoginDto;
import com.isbd.model.Player;

import java.util.Map;

public interface AuthService {
    Player getPlayerByUsername(String username);

    Player getByUsernameAndPassword(String username, String password);

    boolean isPlayerExists(String username);

    Map<String, String> login(LoginDto loginDto);

    Map<String, String> register(LoginDto loginDto);
}
