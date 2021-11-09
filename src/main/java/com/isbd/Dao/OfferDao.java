package com.isbd.Dao;

import com.isbd.model.Offer;

import java.util.List;

public interface OfferDao extends Dao<Offer> {
    List<Offer> getByVillager(int villagerId);

    List<Long> getOfferIdsByVillager(int villagerId);

    List<Offer> getOffersByItemId(int itemId);

    List<Offer> getOffersByItemIdAndAmount(int itemId, int amount);

    List<Offer> getOffersByVillagerId(int villagerId);

    List<Offer> getOffersByReputationLevel(int reputationLevel);

    List<Offer> getOffersByVillagerIdAndItemId(int villagerId, int itemId);

    List<Offer> getOffersByVillagerIdAndItemIdAndAmount(int villagerId, int itemId, int amount);

    List<Offer> getOffersByVillagerIdAndItemIdAndAmountAndReputationLevel(int villagerId, int itemId, int amount,
                                                                          int reputationLevel);

    List<Offer> getOffersByVillagerIdAndReputationLevel(int villagerId, int reputationLevel);

    List<Offer> getOffersByItemIdAndAmountAndReputationLevel(int itemId, int amount, int reputationLevel);

    List<Offer> getOffersByItemIdAndReputationLevel(int itemId, int reputationLevel);
}
