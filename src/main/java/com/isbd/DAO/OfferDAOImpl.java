package com.isbd.DAO;

import com.isbd.model.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfferDAOImpl implements DAO<Offer>, OfferDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Offer> rowMapper;

    @Override
    public Optional<Offer> get(long id) {
        String sql = "select * from offer where offer_id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public List<Offer> getAll() {
        String sql = "select * from offer";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int save(Offer offer) {
        String sql = "insert into offer(villager_id, selling_item_id, amount_of_selling_items, buying_item_id, " +
                "amount_of_buying_items, needed_reputation_level) values(?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql, offer.getVillager_id(), offer.getSellingItem(), offer.getAmountOfSellingItems(),
                offer.getBuyingItem(), offer.getAmountOfBuyingItems(), offer.getReputationLevel());
    }

    @Override
    public int update(Offer offer) {
        String sql = "update offer set villager_id = ?, selling_item_id = ?, amount_of_selling_items = ?, " +
                "buying_item_id = ?, amount_of_buying_items = ?, needed_reputation_level = ? where offer_id = ?";

        return jdbcTemplate.
                update(sql, offer.getVillager_id(), offer.getSellingItem(), offer.getAmountOfSellingItems(),
                        offer.getBuyingItem(), offer.getAmountOfBuyingItems(), offer.getReputationLevel(),
                        offer.getId());
    }

    @Override
    public int delete(Offer offer) {
        return jdbcTemplate.update("delete from offer where offer_id = ?", offer.getId());
    }

    @Override
    public List<Offer> getByVillager(int villagerId) {
        String sql = "select * from offer where villager_id = ?";

        return jdbcTemplate.query(sql, rowMapper, villagerId);
    }

    @Override
    public List<Long> getOfferIdsByVillager(int villagerId) {
        String sql = "select offer_id from offer where villager_id = ?";

        return jdbcTemplate.queryForList(sql, Long.class, villagerId);
    }

    @Override
    public List<Offer> getOffersByItemId(int itemId) {
        String sql = "select * from offer where buying_item_id = ?";

        return jdbcTemplate.query(sql, rowMapper, itemId);
    }

    @Override
    public List<Offer> getOffersByItemIdAndAmount(int itemId, int amount) {
        String sql = "select * from offer where buying_item_id = ? and amount_of_buying_items <= ?";

        return jdbcTemplate.query(sql, rowMapper, itemId, amount);
    }

    @Override
    public List<Offer> getOffersByVillagerId(int villagerId) {
        String sql = "select * from offer where villager_id = ?";

        return jdbcTemplate.query(sql, rowMapper, villagerId);
    }

    @Override
    public List<Offer> getOffersByReputationLevel(int reputationLevel) {
        String sql = "select * from offer where needed_reputation_level = ?";

        return jdbcTemplate.query(sql, rowMapper, reputationLevel);
    }

    @Override
    public List<Offer> getOffersByVillagerIdAndItemId(int villagerId, int itemId) {
        String sql = "select * from offer where villager_id = ? and buying_item_id = ?";

        return jdbcTemplate.query(sql, rowMapper, villagerId, itemId);
    }

    @Override
    public List<Offer> getOffersByVillagerIdAndItemIdAndAmount(int villagerId, int itemId, int amount) {
        String sql = "select * from offer where buying_item_id = ? and amount_of_buying_items <= ? and villager_id = ?";

        return jdbcTemplate.query(sql, rowMapper, itemId, amount, villagerId);
    }

    @Override
    public List<Offer> getOffersByVillagerIdAndItemIdAndAmountAndReputationLevel(int villagerId, int itemId,
                                                                                 int amount, int reputationLevel) {
        String sql = "select * from offer where buying_item_id = ? and amount_of_buying_items <= ? and " +
                "villager_id = ? and needed_reputation_level = ?";

        return jdbcTemplate.query(sql, rowMapper, itemId, amount, villagerId, reputationLevel);
    }

    @Override
    public List<Offer> getOffersByVillagerIdAndReputationLevel(int villagerId, int reputationLevel) {
        String sql = "select * from offer where villager_id = ? and needed_reputation_level = ?";

        return jdbcTemplate.query(sql, rowMapper, villagerId, reputationLevel);
    }

    @Override
    public List<Offer> getOffersByItemIdAndAmountAndReputationLevel(int itemId, int amount, int reputationLevel) {
        String sql = "select * from offer where buying_item_id = ? and amount_of_buying_items <= ? and " +
                "needed_reputation_level = ?";

        return jdbcTemplate.query(sql, rowMapper, itemId, amount, reputationLevel);
    }

    @Override
    public List<Offer> getOffersByItemIdAndReputationLevel(int itemId, int reputationLevel) {
        String sql = "select * from offer where buying_item_id = ? and needed_reputation_level = ?";

        return jdbcTemplate.query(sql, rowMapper, itemId, reputationLevel);
    }
}
