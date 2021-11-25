package com.isbd.repository;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Kit;
import com.isbd.model.ObtainedKit;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class KitObtainmentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final KitRepository kitRepository;

    public List<ObtainedKit> getByPlayer(long playerId) {
        String sql = "select * from kit_obtainment where player_id = ?";

        return jdbcTemplate.query(sql, this::extractData, playerId);
    }

    public Optional<ObtainedKit> getByPlayerAndKit(long playerId, int kitId) {
        String sql = "select * from kit_obtainment where player_id = ? and kit_id = ?";

        List<ObtainedKit> kitList = jdbcTemplate.query(sql, this::extractData, playerId, kitId);
        return kitList.isEmpty() ? Optional.empty() : Optional.of(kitList.get(0));
    }

    private List<ObtainedKit> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ObtainedKit> lastObtainedKits = new ArrayList<>();
        while (rs.next()) {
            int kitId = rs.getInt("kit_id");
            Kit kit = kitRepository.get(kitId).orElseThrow(() ->
                    new EntityNotFoundException(String.format("Kit with id: %d was not found", kitId)));
            ObtainedKit obtainedKit = new ObtainedKit();
            obtainedKit.setLastObtained(rs.getTimestamp("last_obtainment").toLocalDateTime());
            obtainedKit.setId(kit.getId());
            obtainedKit.setName(kit.getName());
            obtainedKit.setItems(kit.getItems());
            lastObtainedKits.add(obtainedKit);
        }
        return lastObtainedKits;
    }
}
