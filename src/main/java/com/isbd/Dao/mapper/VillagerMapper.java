package com.isbd.Dao.mapper;

import com.isbd.Dao.Dao;
import com.isbd.model.Profession;
import com.isbd.model.Village;
import com.isbd.model.Villager;
import com.isbd.service.village.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class VillagerMapper implements RowMapper<Villager> {
    private final VillageService villageService;
    private final Dao<Profession> professionDao;

    @Override
    public Villager mapRow(ResultSet rs, int rowNum) throws SQLException {
        Village village = villageService.getVillage(rs.getInt("village_id"));
        Profession profession = professionDao.get(rs.getInt("profession_id")).get();

        Villager villager = new Villager();
        villager.setId(rs.getInt("villager_id"));
        villager.setName(rs.getString("name"));
        villager.setVillage(village);
        villager.setProfession(profession);
        return villager;
    }
}
