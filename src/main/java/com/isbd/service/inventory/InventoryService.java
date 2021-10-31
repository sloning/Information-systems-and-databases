package com.isbd.service.inventory;

import com.isbd.model.Inventory;

public interface InventoryService {
    Inventory getByPlayerId(long playerId);
}
