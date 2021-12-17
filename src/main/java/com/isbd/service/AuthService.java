package com.isbd.service;

import com.isbd.dao.PlayerDao;
import com.isbd.dto.LoginDto;
import com.isbd.exception.EntityAlreadyExistsException;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.Player;
import com.isbd.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PlayerDao playerDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Player getPlayerByUsername(String username) {
        return playerDao.getByUsername(username).orElseThrow(() ->
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
        return playerDao.getByUsername(username).isPresent();
    }

    public boolean isPlayerExists(long playerId) {
        return playerDao.get(playerId).isPresent();
    }


    public Map<String, String> login(LoginDto loginDTO) {
        Player player = getByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        return getToken(player.getId());
    }

    public Map<String, String> register(LoginDto loginDto) {
        if (isPlayerExists(loginDto.getUsername())) {
            throw new EntityAlreadyExistsException("Пользователь с таким именем уже зарегестрирован");
        }

        Player player = new Player();
        player.setUsername(loginDto.getUsername());
        player.setPassword(bCryptPasswordEncoder.encode(loginDto.getPassword()));
        player = getPlayerByUsername(player.getUsername());
        save(player);
        return getToken(player.getId());
    }

    public void save(Player player) {
        if (playerDao.save(player) == 0) {
            throw new EntityNotSavedException(String.format("Пользователь с идентификатором %d не сохранён",
                    player.getId()));
        }
    }

    private Map<String, String> getToken(Long playerId) {
        String token = jwtTokenProvider.createToken(playerId.toString());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("playerId", playerId.toString());
        return response;
    }
}
