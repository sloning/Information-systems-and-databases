package com.isbd.repository;

import com.isbd.model.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class KitItemsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final InventoryResultSetExtractor inventoryResultSetExtractor;

    public Optional<List<InventoryItem>> get(long kitId) {
        String sql = "select * from kit_composition where kit_id = ?";

        return Optional.ofNullable(jdbcTemplate.query(sql, inventoryResultSetExtractor, kitId));
    }
}
