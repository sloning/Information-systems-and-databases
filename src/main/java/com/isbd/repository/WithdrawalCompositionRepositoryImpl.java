package com.isbd.repository;

import com.isbd.model.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WithdrawalCompositionRepositoryImpl implements WithdrawalCompositionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ResultSetExtractor<List<InventoryItem>> resultSetExtractor;

    public List<InventoryItem> getByWithdrawal(long withdrawalId) {
        String sql = "select * from withdrawal_composition where withdrawal_id = ?";

        return jdbcTemplate.query(sql, resultSetExtractor, withdrawalId);
    }
}
