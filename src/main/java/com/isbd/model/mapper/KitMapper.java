package com.isbd.model.mapper;

import com.isbd.model.InventoryItem;
import com.isbd.model.Kit;
import com.isbd.repository.KitItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KitMapper implements RowMapper<Kit> {
    private final KitItemsRepository kitItemsDao;

    @Override
    public Kit mapRow(ResultSet rs, int rowNum) throws SQLException {
        int kitId = rs.getInt("kit_id");
        List<InventoryItem> items = kitItemsDao.get(kitId).get();

        Kit kit = new Kit();
        kit.setId(kitId);
        kit.setName(rs.getString("name"));
        kit.setItems(items);
        return kit;
    }
}
