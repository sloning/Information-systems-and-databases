package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Player {
    private long id;
    private String username;
    private String password;
    private int tradingExperience;
    private List<AppliedEffect> appliedEffects;
    private List<InventoryItem> inventory;
    private List<Deal> deals;
    private List<Withdrawal> withdrawals;
    private List<ObtainedKit> lastObtainedKits;
}
