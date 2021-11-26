package com.isbd.service;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.WrongCredentialsException;
import com.isbd.model.InventoryItem;
import com.isbd.repository.InventoryRepository;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final AuthenticationFacade authenticationFacade;

    public List<InventoryItem> getByPlayerId(long playerId) {
        if (playerId != authenticationFacade.getPlayerId())
            throw new WrongCredentialsException("У вас не прав на просмотр данной информации");
        return inventoryRepository.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Инвентарь пользователя с идентификатором %d не найден", playerId)));
    }

    public void deleteByPlayerId(long playerId) {
        inventoryRepository.delete(playerId);
    }
}