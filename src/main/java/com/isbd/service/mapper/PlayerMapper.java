package com.isbd.service.mapper;

import com.isbd.dao.InventoryDao;
import com.isbd.dto.PlayerDto;
import com.isbd.model.InventoryItem;
import com.isbd.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlayerMapper {
    private final InventoryDao inventoryDao;

    public PlayerDto createFrom(Player player) {
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
