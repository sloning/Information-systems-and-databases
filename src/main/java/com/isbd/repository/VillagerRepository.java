package com.isbd.repository;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Profession;
import com.isbd.model.Villager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VillagerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProfessionRepository professionRepository;

    public Optional<Villager> get(long id) {
        String sql = "select * from villager where villager_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToVillager), id);
    }

    public List<Villager> getAll() {
        String sql = "select * from villager";

        return jdbcTemplate.query(sql, this::mapRowToVillager);
    }

    public int save(Villager villager) {
        return jdbcTemplate.update("insert into villager(name, profession_id, village_id) values(?, ?, ?)",
                villager.getName(), villager.getProfession().getId(), villager.getVillageId());
    }

    public int update(Villager villager) {
        return jdbcTemplate.
                update("update villager set name = ?, profession_id = ?, village_id = ? where villager_id = ?",
                        villager.getName(), villager.getProfession().getId(), villager.getVillageId(),
                        villager.getId());
    }

    public int delete(Villager villager) {
        return jdbcTemplate.update("delete from villager where village_id = ?", villager.getId());
    }

    public List<Villager> getVillagersByVillage(int villageId) {
        String sql = "select * from villager where village_id = ?";

        return jdbcTemplate.query(sql, this::mapRowToVillager, villageId);
    }

    public Villager mapRowToVillager(ResultSet rs, int rowNum) throws SQLException {
        int professionId = rs.getInt("profession_id");
        Profession profession = professionRepository.get(professionId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Profession with id: %d was not found", professionId)));

        Villager villager = new Villager();
        villager.setId(rs.getInt("villager_id"));
        villager.setName(rs.getString("name"));
        villager.setVillageId(rs.getInt("village_id"));
        villager.setProfession(profession);
        return villager;
    }
}
