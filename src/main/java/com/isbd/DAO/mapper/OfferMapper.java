package com.isbd.DAO.mapper;

import com.isbd.DAO.DAO;
import com.isbd.model.Item;
import com.isbd.model.Offer;
import com.isbd.model.ReputationLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class OfferMapper implements RowMapper<Offer> {
    private final DAO<Item> itemDAO;
    private final DAO<ReputationLevel> reputationLevelDAO;

    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item buyingItem = itemDAO.get(rs.getInt("buying_item_id")).get();
        Item sellingItem = itemDAO.get(rs.getInt("selling_item_id")).get();
        ReputationLevel reputationLevel = reputationLevelDAO.get(rs.getInt("needed_reputation_level")).get();

        Offer offer = new Offer();
        offer.setId(rs.getLong("offer_id"));
        offer.setBuyingItem(buyingItem);
        offer.setSellingItem(sellingItem);
        offer.setAmountOfBuyingItems(rs.getInt("amount_of_buying_items"));
        offer.setAmountOfSellingItems(rs.getInt("amount_of_selling_items"));
        offer.setVillager_id(rs.getInt("villager_id"));
        offer.setReputationLevel(reputationLevel);
        return offer;
    }
}
