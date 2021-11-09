package com.isbd.Dao.mapper;

import com.isbd.Dao.Dao;
import com.isbd.Dao.VillagerDao;
import com.isbd.model.Item;
import com.isbd.model.Offer;
import com.isbd.model.ReputationLevel;
import com.isbd.model.Villager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class OfferMapper implements RowMapper<Offer> {
    private final Dao<Item> itemDao;
    private final Dao<ReputationLevel> reputationLevelDao;
    private final VillagerDao villagerDAO;

    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item buyingItem = itemDao.get(rs.getInt("buying_item_id")).get();
        Item sellingItem = itemDao.get(rs.getInt("selling_item_id")).get();
        ReputationLevel reputationLevel = reputationLevelDao.get(rs.getInt("needed_reputation_level")).get();
        Villager villager = villagerDAO.get(rs.getInt("villager_id")).get();

        Offer offer = new Offer();
        offer.setId(rs.getLong("offer_id"));
        offer.setBuyingItem(buyingItem);
        offer.setSellingItem(sellingItem);
        offer.setAmountOfBuyingItems(rs.getInt("amount_of_buying_items"));
        offer.setAmountOfSellingItems(rs.getInt("amount_of_selling_items"));
        offer.setVillager(villager);
        offer.setReputationLevel(reputationLevel);
        return offer;
    }
}
