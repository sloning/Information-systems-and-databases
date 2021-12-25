package com.isbd.service;

import com.isbd.dao.PlayerDao;
import com.isbd.dto.PlayerDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Player;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.mapper.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerDao playerDao;
    private final AuthenticationFacade authenticationFacade;
    private final PlayerMapper playerMapper;

    public PlayerDto getPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        Player player = playerDao.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Игрок с идентификатором %d не найден", playerId)));
        return playerMapper.createFrom(player);
    }
}
