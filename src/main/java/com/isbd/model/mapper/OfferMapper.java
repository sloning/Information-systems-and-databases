package com.isbd.model.mapper;

import com.isbd.model.Item;
import com.isbd.model.Offer;
import com.isbd.model.ReputationLevel;
import com.isbd.repository.Repository;
import com.isbd.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class OfferMapper implements RowMapper<Offer> {
    private final ItemService itemService;
    private final Repository<ReputationLevel> reputationLevelRepository;

    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item buyingItem = itemService.getItem(rs.getInt("buying_item_id"));
        Item sellingItem = itemService.getItem(rs.getInt("selling_item_id"));
        ReputationLevel reputationLevel = reputationLevelRepository.get(rs.getInt("needed_reputation_level")).get();

        Offer offer = new Offer();
        offer.setId(rs.getLong("offer_id"));
        offer.setBuyingItem(buyingItem);
        offer.setSellingItem(sellingItem);
        offer.setAmountOfBuyingItems(rs.getInt("amount_of_buying_items"));
        offer.setAmountOfSellingItems(rs.getInt("amount_of_selling_items"));
        offer.setVillagerId(rs.getInt("villager_id"));
        offer.setReputationLevel(reputationLevel);
        return offer;
    }
}
