package com.isbd.service;

import com.isbd.dto.LoginDto;
import com.isbd.exception.EntityAlreadyExistsException;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Player;
import com.isbd.repository.PlayerRepository;
import com.isbd.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PlayerRepository playerRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Player getPlayerByUsername(String username) {
        return playerRepository.getByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Неверные имя пользователя или пароль"));
    }

    public Player getByUsernameAndPassword(String username, String password) {
        Player player = getPlayerByUsername(username);

        if (bCryptPasswordEncoder.matches(password, player.getPassword())) {
            return player;
        }
        throw new EntityNotFoundException("Неверный пароль");
    }

    public boolean isPlayerExists(String username) {
        return playerRepository.getByUsername(username).isPresent();
    }

    public boolean isPlayerExists(long playerId) {
        return playerRepository.get(playerId).isPresent();
    }


    public Map<String, String> login(LoginDto loginDTO) {
        Player player = getByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        return getToken(player.getId());
    }

    public Map<String, String> register(LoginDto loginDto) {
        if (isPlayerExists(loginDto.getUsername()))
            throw new EntityAlreadyExistsException("Пользователь с таким именем уже зарегестрирован");

        Player player = new Player();
        player.setUsername(loginDto.getUsername());
        player.setPassword(bCryptPasswordEncoder.encode(loginDto.getPassword()));
        playerRepository.save(player);
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
