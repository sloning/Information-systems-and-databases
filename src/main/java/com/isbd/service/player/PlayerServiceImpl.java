package com.isbd.service.player;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Player;
import com.isbd.repository.Repository;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final Repository<Player> playerRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public Player getPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        return playerRepository.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Игрок с идентификатором %d не найден", playerId)));
    }
}
