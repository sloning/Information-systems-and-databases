package com.isbd.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryItem extends Item {
    private int amount;

    public InventoryItem(int id, String name, String iconAddress, int amount) {
        super(id, name, iconAddress);
        setAmount(amount);
    }
}
