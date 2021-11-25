package com.isbd.repository;

import com.isbd.model.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ResultSetExtractor<List<InventoryItem>> resultSetExtractor;

    public Optional<List<InventoryItem>> get(long playerId) {
        String sql = "select * from inventory where player_id = ?";

        return Optional.ofNullable(jdbcTemplate.query(sql, resultSetExtractor, playerId));
    }

    public int delete(long playerId) {
        String sql = "delete from inventory where player_id = ?";

        return jdbcTemplate.update(sql, playerId);
    }

    public Optional<InventoryItem> getPlayersEmeralds(long playerId) {
        String sql = "select * from inventory where player_id = ? and item_id = 1";

        List<InventoryItem> emeralds = jdbcTemplate.query(sql, resultSetExtractor, playerId);
        if (emeralds.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(emeralds.get(0));
    }
}
