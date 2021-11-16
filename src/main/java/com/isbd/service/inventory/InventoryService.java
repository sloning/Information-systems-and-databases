package com.isbd.service.inventory;

import com.isbd.model.InventoryItem;

import java.util.List;

public interface InventoryService {
    List<InventoryItem> getByPlayerId(long playerId);

    void deleteByPlayerId(long playerId);
}
