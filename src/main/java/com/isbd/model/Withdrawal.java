package com.isbd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Withdrawal {
    private long id;
    private long playerId;
    private int villageId;
    private List<InventoryItem> items;
}
