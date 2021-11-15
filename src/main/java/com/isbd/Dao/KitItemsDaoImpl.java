package com.isbd.Dao;

import com.isbd.model.InventoryItem;
import com.isbd.model.mapper.InventoryResultSetExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class KitItemsDaoImpl implements KitItemsDao {
    private final JdbcTemplate jdbcTemplate;
    private final InventoryResultSetExtractor inventoryResultSetExtractor;

    @Override
    public Optional<List<InventoryItem>> get(long kitId) {
        String sql = "select * from kit_composition where kit_id = ?";

        return Optional.ofNullable(jdbcTemplate.query(sql, inventoryResultSetExtractor, kitId));
    }

    @Override
    public List<List<InventoryItem>> getAll() {
        return null;
    }

    @Override
    public int save(List<InventoryItem> inventoryItems) {
        return 0;
    }

    @Override
    public int update(List<InventoryItem> inventoryItems) {
        return 0;
    }

    @Override
    public int delete(List<InventoryItem> inventoryItems) {
        return 0;
    }
}
