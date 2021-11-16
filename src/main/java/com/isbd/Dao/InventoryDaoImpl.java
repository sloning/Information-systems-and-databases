package com.isbd.Dao;

import com.isbd.model.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryDaoImpl implements InventoryDao {
    private final JdbcTemplate jdbcTemplate;
    private final ResultSetExtractor<List<InventoryItem>> resultSetExtractor;

    @Override
    public Optional<List<InventoryItem>> get(long playerId) {
        String sql = "select * from inventory where player_id = ?";

        return Optional.ofNullable(jdbcTemplate.query(sql, resultSetExtractor, playerId));
    }

    @Override
    public int delete(long playerId) {
        String sql = "delete from inventory where player_id = ?";

        return jdbcTemplate.update(sql, playerId);
    }
}
