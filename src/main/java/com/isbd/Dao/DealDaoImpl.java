package com.isbd.Dao;

import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.Deal;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DealDaoImpl implements Dao<Deal>, DealDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Deal> rowMapper;

    @Override
    public Optional<Deal> get(long id) {
        String sql = "select * from deal where deal_id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public List<Deal> getByPlayer(long playerId) {
        String sql = "select * from deal where player_id = ?";

        return jdbcTemplate.query(sql, rowMapper, playerId);
    }

    @Override
    public List<Deal> getAll() {
        String sql = "select * from deal";

        return jdbcTemplate.query(sql, rowMapper);
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
}
