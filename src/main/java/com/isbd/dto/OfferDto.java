package com.isbd.dto;

import com.isbd.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    private long offerId;
    private Item sellingItem;
    private Item buyingItem;
    private int amountOfSellingItems;
    private int amountOfBuyingItems;
    private int amountOfBuyingItemsWithDiscount;
    private boolean hasDiscount;
    private OfferAvailability offerAvailability;
}
