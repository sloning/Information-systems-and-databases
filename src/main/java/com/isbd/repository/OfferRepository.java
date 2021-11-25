package com.isbd.repository;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Item;
import com.isbd.model.Offer;
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
public class OfferRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ItemService itemService;
    private final ReputationLevelRepository reputationLevelRepository;

    public Optional<Offer> get(long id) {
        String sql = "select * from offer where offer_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToOffer), id);
    }

    public List<Offer> getAll() {
        String sql = "select * from offer";

        return jdbcTemplate.query(sql, this::mapRowToOffer);
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

    public List<Offer> getAllWithPagination(int limit, int offset) {
        String sql = "select * from offer limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, limit, offset);
    }

    public List<Offer> getOffersByItemId(int itemId, int limit, int offset) {
        String sql = "select * from offer where buying_item_id = ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, itemId, limit, offset);
    }

    public List<Offer> getOffersByItemIdAndAmount(int itemId, int amount, int limit, int offset) {
        String sql = "select * from offer where buying_item_id = ? and amount_of_buying_items <= ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, itemId, amount, limit, offset);
    }

    public List<Offer> getOffersByVillagerId(int villagerId, int limit, int offset) {
        String sql = "select * from offer where villager_id = ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, villagerId, limit, offset);
    }

    public List<Offer> getOffersByReputationLevel(int reputationLevel, int limit, int offset) {
        String sql = "select * from offer where needed_reputation_level <= ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, reputationLevel, limit, offset);
    }

    public List<Offer> getOffersByVillagerIdAndItemId(int villagerId, int itemId, int limit, int offset) {
        String sql = "select * from offer where villager_id = ? and buying_item_id = ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, villagerId, itemId, limit, offset);
    }

    public List<Offer> getOffersByVillagerIdAndItemIdAndAmount(int villagerId, int itemId, int amount,
                                                               int limit, int offset) {
        String sql = "select * from offer where buying_item_id = ? and amount_of_buying_items <= ? and villager_id = ?" +
                " limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, itemId, amount, villagerId, limit, offset);
    }

    public List<Offer> getOffersByVillagerIdAndItemIdAndAmountAndReputationLevel(int villagerId, int itemId,
                                                                                 int amount, int reputationLevel,
                                                                                 int limit, int offset) {
        String sql = "select * from offer where buying_item_id = ? and amount_of_buying_items <= ? and " +
                "villager_id = ? and needed_reputation_level <= ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, itemId, amount, villagerId, reputationLevel, limit, offset);
    }

    public List<Offer> getOffersByVillagerIdAndReputationLevel(int villagerId, int reputationLevel,
                                                               int limit, int offset) {
        String sql = "select * from offer where villager_id = ? and needed_reputation_level <= ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, villagerId, reputationLevel, limit, offset);
    }

    public List<Offer> getOffersByItemIdAndAmountAndReputationLevel(int itemId, int amount, int reputationLevel,
                                                                    int limit, int offset) {
        String sql = "select * from offer where buying_item_id = ? and amount_of_buying_items <= ? and " +
                "needed_reputation_level <= ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, itemId, amount, reputationLevel, limit, offset);
    }

    public List<Offer> getOffersByItemIdAndReputationLevel(int itemId, int reputationLevel, int limit, int offset) {
        String sql = "select * from offer where buying_item_id = ? and needed_reputation_level <= ? limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToOffer, itemId, reputationLevel, limit, offset);
    }

    private Offer mapRowToOffer(ResultSet rs, int rowNum) throws SQLException {
        int neededReputationLevel = rs.getInt("needed_reputation_level");
        Item buyingItem = itemService.getItem(rs.getInt("buying_item_id"));
        Item sellingItem = itemService.getItem(rs.getInt("selling_item_id"));
        ReputationLevel reputationLevel = reputationLevelRepository.get(neededReputationLevel).orElseThrow(() ->
                new EntityNotFoundException(String.format("Reputation level with id: %d was not found",
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
