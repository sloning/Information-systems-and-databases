package com.isbd.repository;

import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.Deal;
import com.isbd.model.Offer;
import com.isbd.service.offer.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DealRepositoryImpl implements DealRepository {
    private final JdbcTemplate jdbcTemplate;
    private final OfferService offerService;

    @Override
    public Optional<Deal> get(long id) {
        String sql = "select * from deal where deal_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToDeal), id);
    }

    @Override
    public List<Deal> getByPlayer(long playerId) {
        String sql = "select * from deal where player_id = ?";

        return jdbcTemplate.query(sql, this::mapRowToDeal, playerId);
    }

    @Override
    public List<Deal> getAll() {
        String sql = "select * from deal";

        return jdbcTemplate.query(sql, this::mapRowToDeal);
    }

    @Override
    public int save(Deal deal) {
        int rowsAffected = 0;
        String sql = "insert into deal(offer_id, player_id, time) values(?, ?, ?)";

        try {
            rowsAffected += jdbcTemplate.update(sql, deal.getOffer().getId(), deal.getPlayerId(), deal.getTime());
        } catch (DataAccessException e) {
            if (e instanceof UncategorizedSQLException)
                throw new EntityNotSavedException(String.format("Player: %d has not enough items", deal.getPlayerId()));
        }
        return rowsAffected;
    }

    @Override
    public int update(Deal deal) {
        return 0;
    }

    @Override
    public int delete(Deal deal) {
        return 0;
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
