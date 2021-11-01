package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Withdrawal {
    private long id;
    private long playerId;
    private Village village;
    private List<InventoryItem> items;
}
