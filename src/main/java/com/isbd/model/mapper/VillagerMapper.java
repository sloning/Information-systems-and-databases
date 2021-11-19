package com.isbd.model.mapper;

import com.isbd.Dao.Dao;
import com.isbd.model.Profession;
import com.isbd.model.Villager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class VillagerMapper implements RowMapper<Villager> {
    private final Dao<Profession> professionDao;

    @Override
    public Villager mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profession profession = professionDao.get(rs.getInt("profession_id")).get();

        Villager villager = new Villager();
        villager.setId(rs.getInt("villager_id"));
        villager.setName(rs.getString("name"));
        villager.setVillageId(rs.getInt("village_id"));
        villager.setProfession(profession);
        return villager;
    }
}
