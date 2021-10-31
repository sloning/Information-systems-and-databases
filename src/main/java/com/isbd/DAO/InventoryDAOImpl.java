package com.isbd.DAO;

import com.isbd.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryDAOImpl implements InventoryDAO {
    private final JdbcTemplate jdbcTemplate;
    private final ResultSetExtractor<Inventory> resultSetExtractor;

    @Override
    public Optional<Inventory> get(long playerId) {
        String sql = "select * from inventory where player_id = ?";

        return Optional.ofNullable(jdbcTemplate.query(sql, resultSetExtractor, playerId));
    }

    @Override
    public List<Inventory> getAll() {
        // TODO implement
        return null;
    }

    @Override
    public int save(Inventory inventory) {
//        String sql = "insert into inventory values(?, ?, ?)";
//        int rowsAffected = 0;
//
//        for (Item item : inventory.getItems()) {
//            rowsAffected += jdbcTemplate.update(sql, inventory.getPlayerId(), item.getId(), inventory.getItemAmount(item));
//        }
//
//        return rowsAffected;
        return 0;
    }

    @Override
    public int update(Inventory inventory) {
        // TODO implement
        return 0;
    }

    @Override
    public int delete(Inventory inventory) {
        // TODO implement
        return 0;
    }
}
