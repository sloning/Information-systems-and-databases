package com.isbd.repository;

import com.isbd.model.InventoryItem;

import java.util.List;

public interface WithdrawalCompositionRepository {
    List<InventoryItem> getByWithdrawal(long withdrawalId);
}
