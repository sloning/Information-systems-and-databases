package com.isbd.DAO;

import com.isbd.model.InventoryItem;

import java.util.List;

public interface WithdrawalCompositionDao {
    List<InventoryItem> getByWithdrawal(long withdrawalId);
}
