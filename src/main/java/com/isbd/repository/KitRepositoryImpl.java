package com.isbd.repository;

import com.isbd.model.Kit;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
@RequiredArgsConstructor
public class KitRepositoryImpl implements Repository<Kit>, KitRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Kit> rowMapper;

    @Override
    public Optional<Kit> get(long kitId) {
        String sql = "select * from kit where kit_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(rowMapper), kitId);
    }

    @Override
    public List<Kit> getAll() {
        String sql = "select * from kit";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int save(Kit kit) {
        return 0;
    }

    @Override
    public int update(Kit kit) {
        return 0;
    }

    @Override
    public int delete(Kit kit) {
        return 0;
    }

    @Override
    public void obtainKit(long playerId, int kitId) {
        String sql = "select give_kit(?, ?)";
        List<SqlParameter> parameters = List.of(new SqlParameter(Types.NVARCHAR));

        jdbcTemplate.call(con -> {
            CallableStatement cs = con.prepareCall(sql);
            cs.setLong(1, playerId);
            cs.setInt(2, kitId);
            return cs;
        }, parameters);
    }
}
