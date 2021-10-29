package com.isbd.DAO;

import com.isbd.model.Offer;

import java.util.List;

public interface OfferDAO extends DAO<Offer> {
    List<Offer> getByVillager(int villager_id);

    List<Long> getOfferIdsByVillager(int villager_id);
}
