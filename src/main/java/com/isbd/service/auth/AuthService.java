package com.isbd.service.auth;

import com.isbd.Dto.UserDto;
import com.isbd.model.Player;

import java.util.Map;

public interface AuthService {
    Player getPlayerByUsername(String username);

    Player getByUsernameAndPassword(String username, String password);

    boolean isPlayerExists(String username);

    Map<String, String> login(UserDto userDTO);

    Map<String, String> register(UserDto userDTO);
}
