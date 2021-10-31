package com.isbd.service.inventory;

import com.isbd.DAO.InventoryDAO;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryDAO inventoryDAO;

    @Override
    public Inventory getByPlayerId(long playerId) {
        return inventoryDAO.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Inventory of user: %d was not found", playerId)));
    }
}
