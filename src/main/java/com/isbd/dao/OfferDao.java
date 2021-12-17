package com.isbd.dao;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Item;
import com.isbd.model.Offer;
import com.isbd.model.Pageable;
import com.isbd.model.ReputationLevel;
import com.isbd.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfferDao {
    private final JdbcTemplate jdbcTemplate;
    private final ItemService itemService;
    private final ReputationLevelDao reputationLevelDao;

    public Optional<Offer> get(long id) {
        String sql = "select * from offer where offer_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToOffer), id);
    }

    public List<Offer> getAll() {
        String sql = "select * from offer";

        return jdbcTemplate.query(sql, this::mapRowToOffer);
    }

    public List<Offer> getAll(Pageable pageable) {
        String sql = "select * from offer limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, pageable.getLimit(), pageable.getOffset());
    }

    public int save(Offer offer) {
        String sql = "insert into offer(villager_id, selling_item_id, amount_of_selling_items, buying_item_id, " +
                "amount_of_buying_items, needed_reputation_level) values(?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql, offer.getVillagerId(), offer.getSellingItem(), offer.getAmountOfSellingItems(),
                offer.getBuyingItem(), offer.getAmountOfBuyingItems(), offer.getReputationLevel());
    }

    public int update(Offer offer) {
        String sql = "update offer set villager_id = ?, selling_item_id = ?, amount_of_selling_items = ?, " +
                "buying_item_id = ?, amount_of_buying_items = ?, needed_reputation_level = ? where offer_id = ?";

        return jdbcTemplate.
                update(sql, offer.getVillagerId(), offer.getSellingItem(), offer.getAmountOfSellingItems(),
                        offer.getBuyingItem(), offer.getAmountOfBuyingItems(), offer.getReputationLevel(),
                        offer.getId());
    }

    public int delete(Offer offer) {
        return jdbcTemplate.update("delete from offer where offer_id = ?", offer.getId());
    }

    public List<Offer> getOffersByVillagerId(int villagerId, Pageable pageable) {
        String sql = "select * from offer where villager_id = ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, villagerId, pageable.getLimit(), pageable.getOffset());
    }

    public List<Offer> getOffersByVillagerIdAndReputationLevel(int villagerId, int reputationLevel, Pageable pageable) {
        String sql = "select * from offer where villager_id = ? and needed_reputation_level <= ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, villagerId, reputationLevel,
                pageable.getLimit(), pageable.getOffset());
    }

    private Offer mapRowToOffer(ResultSet rs, int rowNum) throws SQLException {
        int neededReputationLevel = rs.getInt("needed_reputation_level");
        Item buyingItem = itemService.getItem(rs.getInt("buying_item_id"));
        Item sellingItem = itemService.getItem(rs.getInt("selling_item_id"));
        ReputationLevel reputationLevel = reputationLevelDao.get(neededReputationLevel).orElseThrow(() ->
                new EntityNotFoundException(String.format("Уровень репутации с идентификатором %d не найден",
                        neededReputationLevel)));

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
