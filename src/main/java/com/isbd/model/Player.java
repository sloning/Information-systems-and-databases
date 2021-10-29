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
    private List<Effect> effects;
    private Inventory inventory;
    private List<Deal> deals;
    private List<Withdrawal> withdrawals;
}
