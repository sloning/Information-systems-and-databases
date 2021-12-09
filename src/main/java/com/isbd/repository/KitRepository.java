package com.isbd.repository;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.InventoryItem;
import com.isbd.model.Kit;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class KitRepository {
    private final JdbcTemplate jdbcTemplate;
    private final KitItemsRepository kitItemsRepository;

    public Optional<Kit> get(long kitId) {
        String sql = "select * from kit where kit_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToKit), kitId);
    }

    public List<Kit> getAll() {
        String sql = "select * from kit";

        return jdbcTemplate.query(sql, this::mapRowToKit);
    }

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

    private Kit mapRowToKit(ResultSet rs, int rowNum) throws SQLException {
        int kitId = rs.getInt("kit_id");
        List<InventoryItem> items = kitItemsRepository.get(kitId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Предметы для набора %d не найдены", kitId)));

        Kit kit = new Kit();
        kit.setId(kitId);
        kit.setName(rs.getString("name"));
        kit.setItems(items);
        return kit;
    }
}
