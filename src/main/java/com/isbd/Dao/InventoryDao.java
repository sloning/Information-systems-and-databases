package com.isbd.Dao;

import com.isbd.model.InventoryItem;

import java.util.List;
import java.util.Optional;

public interface InventoryDao {
    Optional<List<InventoryItem>> get(long playerId);

    int delete(long playerId);
}
