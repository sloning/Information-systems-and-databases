package com.isbd.model.mapper;

import com.isbd.Dao.KitDao;
import com.isbd.model.Kit;
import com.isbd.model.ObtainedKit;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KitObtainmentResultSetExtractor implements ResultSetExtractor<List<ObtainedKit>> {
    private final KitDao kitDao;

    @Override
    public List<ObtainedKit> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ObtainedKit> lastObtainedKits = new ArrayList<>();
        while (rs.next()) {
            Kit kit = kitDao.get(rs.getInt("kit_id")).get();
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
