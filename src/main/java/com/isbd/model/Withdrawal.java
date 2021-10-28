package com.isbd.model;

import lombok.Data;

@Data
public class Withdrawal {
    private final long id;
    private Village village;
    private Inventory inventory;
}
