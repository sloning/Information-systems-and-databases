package com.isbd.service.auth;

import com.isbd.DTO.UserDTO;
import com.isbd.model.Player;

import java.util.Map;

public interface AuthService {
    Player getPlayerByUsername(String username);

    Player getByUsernameAndPassword(String username, String password);

    boolean isPlayerExists(String username);

    Map<String, String> login(UserDTO userDTO);

    Map<String, String> register(UserDTO userDTO);
}
