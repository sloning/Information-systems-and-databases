package com.isbd.service.inventory;

import com.isbd.DAO.InventoryDAO;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryDAO inventoryDAO;

    @Override
    public List<InventoryItem> getByPlayerId(long playerId) {
        return inventoryDAO.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Inventory of user: %d was not found", playerId)));
    }
}
