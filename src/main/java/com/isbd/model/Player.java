package com.isbd.model;

import lombok.Data;

import java.util.List;

@Data
public class Player {
    private final long id;
    private String username;
    private String password;
    private int tradingExperience;
    private List<Effect> effects;
    private List<Item> inventory;
    private List<Deal> deals;
    private List<Withdrawal> withdrawals;
}
