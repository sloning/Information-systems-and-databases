package com.isbd.service;

import com.isbd.dao.InventoryDao;
import com.isbd.dao.PlayerDao;
import com.isbd.dto.PlayerDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.InventoryItem;
import com.isbd.model.Player;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerDao playerDao;
    private final AuthenticationFacade authenticationFacade;
    private final InventoryDao inventoryDao;

    public PlayerDto getPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        Player player = playerDao.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Игрок с идентификатором %d не найден", playerId)));
        return convertToPlayerDto(player);
    }

    private PlayerDto convertToPlayerDto(Player player) {
        Optional<InventoryItem> optionalInventoryItem = inventoryDao.getPlayersEmeralds(player.getId());
        int amountOfEmeralds = optionalInventoryItem.map(InventoryItem::getAmount).orElse(0);

        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setUsername(player.getUsername());
        playerDto.setTradingExperience(player.getTradingExperience());
        playerDto.setAmountOfEmeralds(amountOfEmeralds);

        return playerDto;
    }
}
