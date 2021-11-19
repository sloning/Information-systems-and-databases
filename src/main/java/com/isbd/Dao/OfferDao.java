package com.isbd.Dao;

import com.isbd.model.Offer;

import java.util.List;

public interface OfferDao extends Dao<Offer> {
    List<Offer> getAllWithPagination(int limit, int offset);

    List<Offer> getByVillager(int villagerId, int limit, int offset);

    List<Long> getOfferIdsByVillager(int villagerId, int limit, int offset);

    List<Offer> getOffersByItemId(int itemId, int limit, int offset);

    List<Offer> getOffersByItemIdAndAmount(int itemId, int amount, int limit, int offset);

    List<Offer> getOffersByVillagerId(int villagerId, int limit, int offset);

    List<Offer> getOffersByReputationLevel(int reputationLevel, int limit, int offset);

    List<Offer> getOffersByVillagerIdAndItemId(int villagerId, int itemId, int limit, int offset);

    List<Offer> getOffersByVillagerIdAndItemIdAndAmount(int villagerId, int itemId, int amount, int limit, int offset);

    List<Offer> getOffersByVillagerIdAndItemIdAndAmountAndReputationLevel(int villagerId, int itemId, int amount,
                                                                          int reputationLevel, int limit, int offset);

    List<Offer> getOffersByVillagerIdAndReputationLevel(int villagerId, int reputationLevel, int limit, int offset);

    List<Offer> getOffersByItemIdAndAmountAndReputationLevel(int itemId, int amount, int reputationLevel,
                                                             int limit, int offset);

    List<Offer> getOffersByItemIdAndReputationLevel(int itemId, int reputationLevel, int limit, int offset);
}
