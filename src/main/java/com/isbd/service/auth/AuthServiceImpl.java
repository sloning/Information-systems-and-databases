package com.isbd.service.auth;

import com.isbd.DAO.PlayerDAO;
import com.isbd.DTO.UserDTO;
import com.isbd.exception.EntityAlreadyExists;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Player;
import com.isbd.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PlayerDAO playerDAO;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Player getPlayerByUsername(String username) {
        return playerDAO.getByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with username: %s was not found", username)));
    }

    @Override
    public Player getByUsernameAndPassword(String username, String password) {
        Player player = getPlayerByUsername(username);

        if (bCryptPasswordEncoder.matches(password, player.getPassword())) return player;
        throw new EntityNotFoundException("Incorrect password");
    }

    @Override
    public boolean isPlayerExists(String username) {
        return playerDAO.getByUsername(username).isPresent();
    }

    @Override
    public Map<String, String> login(UserDTO userDTO) {
        UserValidator.validateUser(userDTO);
        Player player = getByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());

        return getToken(player.getId(), player.getUsername());
    }

    @Override
    public Map<String, String> register(UserDTO userDTO) {
        UserValidator.validateUser(userDTO);
        if (isPlayerExists(userDTO.getUsername()))
            throw new EntityAlreadyExists(String.format("Player with username: %s already exists",
                    userDTO.getUsername()));

        Player player = new Player();
        player.setUsername(userDTO.getUsername());
        player.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        playerDAO.save(player);

        return getToken(player.getId(), player.getUsername());
    }

    private Map<String, String> getToken(long playerId, String username) {
        String token = jwtTokenProvider.createToken(playerId);

        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);
        return response;
    }
}
