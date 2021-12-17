package com.isbd.dao;

import com.isbd.model.InventoryItem;
import com.isbd.model.Item;
import com.isbd.service.ItemService;
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
public class InventoryResultSetExtractor implements ResultSetExtractor<List<InventoryItem>> {
    private final ItemService itemService;

    @Override
    public List<InventoryItem> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<InventoryItem> items = new ArrayList<>();
        while (rs.next()) {
            Item item = itemService.getItem(rs.getInt("item_id"));
            InventoryItem inventoryItem = new InventoryItem(item.getId(), item.getName(), item.getIconAddress(),
                    rs.getInt("amount"));
            items.add(inventoryItem);
        }

        return items;
    }
}
