package com.isbd.service;

import com.isbd.dao.InventoryDao;
import com.isbd.exception.WrongCredentialsException;
import com.isbd.model.InventoryItem;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryDao inventoryDao;
    private final AuthenticationFacade authenticationFacade;

    public List<InventoryItem> getByPlayerId(long playerId) {
        if (playerId != authenticationFacade.getPlayerId())
            throw new WrongCredentialsException("У вас не прав на просмотр данной информации");
        return inventoryDao.getNonZeroItems(playerId);
    }

    public void deleteByPlayerId(long playerId) {
        inventoryDao.delete(playerId);
    }
}
