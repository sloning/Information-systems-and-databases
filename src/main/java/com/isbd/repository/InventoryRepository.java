package com.isbd.repository;

import com.isbd.model.InventoryItem;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    Optional<List<InventoryItem>> get(long playerId);

    int delete(long playerId);
}
