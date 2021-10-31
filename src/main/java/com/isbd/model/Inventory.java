package com.isbd.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory {
    private final Map<Item, Integer> itemMap;
    private long playerId;

    public Inventory() {
        this.itemMap = new HashMap<>();
    }

    public Inventory(long playerId) {
        this.itemMap = new HashMap<>();
        this.playerId = playerId;
    }

    public Set<Item> getItems() {
        return itemMap.keySet();
    }

    public int getItemAmount(Item item) {
        return itemMap.get(item);
    }

    public void addItem(Item item, int amount) {
        itemMap.put(item, amount);
    }

    public void addAmount(Item item, int amount) {
        int current_amount = itemMap.get(item);
        itemMap.put(item, current_amount + amount);
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
