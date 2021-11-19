package com.isbd.service.auth;

import com.isbd.Dao.PlayerDao;
import com.isbd.Dto.UserDto;
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
    private final PlayerDao playerDAO;
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
    public Map<String, String> login(UserDto userDTO) {
        Player player = getByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());

        return getToken(player.getId());
    }

    @Override
    public Map<String, String> register(UserDto userDTO) {
        if (isPlayerExists(userDTO.getUsername()))
            throw new EntityAlreadyExists(String.format("User with username: %s already exists",
                    userDTO.getUsername()));

        Player player = new Player();
        player.setUsername(userDTO.getUsername());
        player.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        playerDAO.save(player);
        player = getPlayerByUsername(player.getUsername());

        return getToken(player.getId());
    }

    private Map<String, String> getToken(Long playerId) {
        String token = jwtTokenProvider.createToken(playerId.toString());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("playerId", playerId.toString());
        return response;
    }
}
