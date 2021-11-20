package com.isbd.model.mapper;

import com.isbd.model.Profession;
import com.isbd.model.Villager;
import com.isbd.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class VillagerMapper implements RowMapper<Villager> {
    private final Repository<Profession> professionRepository;

    @Override
    public Villager mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profession profession = professionRepository.get(rs.getInt("profession_id")).get();

        Villager villager = new Villager();
        villager.setId(rs.getInt("villager_id"));
        villager.setName(rs.getString("name"));
        villager.setVillageId(rs.getInt("village_id"));
        villager.setProfession(profession);
        return villager;
    }
}
