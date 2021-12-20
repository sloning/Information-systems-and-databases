package com.isbd.dao;

import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.Deal;
import com.isbd.model.Offer;
import com.isbd.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DealDao {
    private final JdbcTemplate jdbcTemplate;
    private final OfferService offerService;

    public Optional<Deal> get(long id) {
        String sql = "select * from deal where deal_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToDeal), id);
    }

    public List<Deal> getByPlayer(long playerId) {
        String sql = "select * from deal where player_id = ?";

        return jdbcTemplate.query(sql, this::mapRowToDeal, playerId);
    }

    public List<Deal> getAll() {
        String sql = "select * from deal";

        return jdbcTemplate.query(sql, this::mapRowToDeal);
    }

    public int save(Deal deal) {
        int rowsAffected = 0;
        String sql = "insert into deal(offer_id, player_id, time) values(?, ?, ?)";

        try {
            rowsAffected += jdbcTemplate.update(sql, deal.getOffer().getId(), deal.getPlayerId(), deal.getTime());
        } catch (UncategorizedSQLException e) {
            throw new EntityNotSavedException("Недостаточно предметов");
        }
        return rowsAffected;
    }

    private Deal mapRowToDeal(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = offerService.getOffer(rs.getLong("offer_id"));

        Deal deal = new Deal();
        deal.setId(rs.getLong("deal_id"));
        deal.setOffer(offer);
        deal.setTime(rs.getTimestamp("time").toLocalDateTime());
        deal.setPlayerId(rs.getLong("player_id"));
        return deal;
    }
}
