package com.isbd.repository;

import com.isbd.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Item> get(long id) {
        String sql = "select * from item where item_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToItem), id);
    }

    @Override
    public List<Item> getAll() {
        String sql = "select * from item";

        return jdbcTemplate.query(sql, this::mapRowToItem);
    }

    @Override
    public int save(Item item) {
        return jdbcTemplate.update("insert into item(name, icon_address) values(?, ?)",
                item.getName(), item.getIconAddress());
    }

    @Override
    public int update(Item item) {
        return jdbcTemplate.
                update("update item set name = ?, icon_address = ? where item_id = ?",
                        item.getName(), item.getIconAddress(), item.getId());
    }

    @Override
    public int delete(Item item) {
        return jdbcTemplate.update("delete from item where item_id = ?", item.getId());
    }

    @Override
    public Optional<String> getIconAddress(int id) {
        String sql = "select icon_address from item where item_id = ?";

        return jdbcTemplate.query(sql,
                ResultSetExtractorFactory.optionalExtractor(BeanPropertyRowMapper.newInstance(String.class)), id);
    }

    private Item mapRowToItem(ResultSet rs, int rowNum) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("item_id"));
        item.setName(rs.getString("name"));
        item.setIconAddress(rs.getString("icon_address"));
        return item;
    }
}