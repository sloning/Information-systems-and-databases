package com.isbd.DAO.mapper;

import com.isbd.DAO.DAO;
import com.isbd.model.Profession;
import com.isbd.model.Village;
import com.isbd.model.Villager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class VillagerMapper implements RowMapper<Villager> {
    private final DAO<Village> villageDAO;
    private final DAO<Profession> professionDAO;

    @Override
    public Villager mapRow(ResultSet rs, int rowNum) throws SQLException {
        int villagerId = rs.getInt("villager_id");
        Village village = villageDAO.get(rs.getInt("village_id")).get();
        Profession profession = professionDAO.get(rs.getInt("profession_id")).get();

        Villager villager = new Villager();
        villager.setId(villagerId);
        villager.setName(rs.getString("name"));
        villager.setVillage(village);
        villager.setProfession(profession);
        return villager;
    }
}
