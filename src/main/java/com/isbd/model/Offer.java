package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Offer {
    private long id;
    private int villager_id;
    private Item sellingItem;
    private Item buyingItem;
    private int amountOfSellingItems;
    private int amountOfBuyingItems;
    private ReputationLevel reputationLevel;
}
