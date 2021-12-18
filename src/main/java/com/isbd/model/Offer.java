package com.isbd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    private long id;
    private int villagerId;
    private Item sellingItem;
    private Item buyingItem;
    private int amountOfSellingItems;
    private int amountOfBuyingItems;
    private ReputationLevel reputationLevel;
}
