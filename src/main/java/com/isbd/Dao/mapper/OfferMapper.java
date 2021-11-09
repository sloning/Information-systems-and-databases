package com.isbd.Dao.mapper;

import com.isbd.Dao.Dao;
import com.isbd.model.Item;
import com.isbd.model.Offer;
import com.isbd.model.ReputationLevel;
import com.isbd.model.Villager;
import com.isbd.service.item.ItemService;
import com.isbd.service.villager.VillagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class OfferMapper implements RowMapper<Offer> {
    private final ItemService itemService;
    private final Dao<ReputationLevel> reputationLevelDao;
    private final VillagerService villagerService;

    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item buyingItem = itemService.getItem(rs.getInt("buying_item_id"));
        Item sellingItem = itemService.getItem(rs.getInt("selling_item_id"));
        ReputationLevel reputationLevel = reputationLevelDao.get(rs.getInt("needed_reputation_level")).get();
        Villager villager = villagerService.getVillager(rs.getInt("villager_id"));

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
