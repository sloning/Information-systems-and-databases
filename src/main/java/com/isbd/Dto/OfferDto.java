package com.isbd.Dto;

import com.isbd.model.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfferDto {
    private long offerId;
    private Item sellingItem;
    private Item buyingItem;
    private int amountOfSellingItems;
    private int amountOfBuyingItems;
    private int amountOfBuyingItemsWithDiscount;
    private boolean hasDiscount;
    private boolean canBeTraded;
}
