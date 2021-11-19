package com.isbd.service.player;

import com.isbd.Dao.Dao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Player;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final Dao<Player> playerDao;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public Player getPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        return playerDao.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Player with id: %d was not found", playerId)));
    }
}
