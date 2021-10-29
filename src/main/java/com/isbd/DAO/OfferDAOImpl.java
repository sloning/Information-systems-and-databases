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

    public List<Offer> getByVillager(int villager_id) {
        String sql = "select * from offer where villager_id = ?";

        return jdbcTemplate.query(sql, rowMapper, villager_id);
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
}
