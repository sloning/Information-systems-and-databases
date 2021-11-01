package com.isbd.DAO.mapper;

import com.isbd.DAO.ItemDAO;
import com.isbd.model.Inventory;
import com.isbd.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class InventoryResultSetExtractor implements ResultSetExtractor<Inventory> {
    private final ItemDAO itemDAO;

    @Override
    public Inventory extractData(ResultSet rs) throws SQLException, DataAccessException {
        Inventory inventory = new Inventory();
        while (rs.next()) {
            Item item = itemDAO.get(rs.getInt("item_id")).get();
            int amount = rs.getInt("amount");
            inventory.addItem(item, amount);
        }

        return inventory;
    }
}
