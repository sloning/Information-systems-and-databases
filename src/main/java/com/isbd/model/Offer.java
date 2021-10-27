package com.isbd.model;

import lombok.Data;

@Data
public class Offer {
    private final int id;
    private Villager villager;
    private Item sellingItem;
    private Item buyingItem;
    private int amountOfSellingItems;
    private int amountOfBuyingItems;
    private ReputationLevel reputationLevel;
}
