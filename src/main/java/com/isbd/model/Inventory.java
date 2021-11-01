package com.isbd.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory {
    private final Map<Item, Integer> itemMap;

    public Inventory() {
        this.itemMap = new HashMap<>();
    }

    public Set<Item> getItems() {
        return itemMap.keySet();
    }

    public Collection<Integer> getAmounts() {
        return itemMap.values();
    }

    public int getAmount(Item item) {
        return itemMap.get(item);
    }

    public void addItem(Item item, int amount) {
        itemMap.put(item, amount);
    }

    public void addAmount(Item item, int amount) {
        int current_amount = itemMap.get(item);
        itemMap.put(item, current_amount + amount);
    }
}
