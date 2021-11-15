package com.isbd.Dao;

import com.isbd.model.ObtainedKit;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class KitObtainmentDaoImpl implements KitObtainmentDao {
    private final JdbcTemplate jdbcTemplate;
    private final ResultSetExtractor<List<ObtainedKit>> resultSetExtractor;

    @Override
    public List<ObtainedKit> getByKit(int kitId) {
        String sql = "select * from kit_obtainment where kit_id = ?";

        return jdbcTemplate.query(sql, resultSetExtractor, kitId);
    }

    @Override
    public List<ObtainedKit> getByPlayer(long playerId) {
        String sql = "select * from kit_obtainment where player_id = ?";

        return jdbcTemplate.query(sql, resultSetExtractor, playerId);
    }

    @Override
    public Optional<ObtainedKit> getByPlayerAndKit(long playerId, int kitId) {
        String sql = "select * from kit_obtainment where player_id = ? and kit_id = ?";

        List<ObtainedKit> kitList = jdbcTemplate.query(sql, resultSetExtractor, playerId, kitId);
        return kitList.isEmpty() ? Optional.empty() : Optional.of(kitList.get(0));
    }
}
