package com.isbd.repository;

import com.isbd.model.Villager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
@RequiredArgsConstructor
public class VillagerRepositoryImpl implements Repository<Villager>, VillagerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Villager> rowMapper;


    @Override
    public Optional<Villager> get(long id) {
        String sql = "select * from villager where villager_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(rowMapper), id);
    }

    @Override
    public List<Villager> getAll() {
        String sql = "select * from villager";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int save(Villager villager) {
        return jdbcTemplate.update("insert into villager(name, profession_id, village_id) values(?, ?, ?)",
                villager.getName(), villager.getProfession().getId(), villager.getVillageId());
    }

    @Override
    public int update(Villager villager) {
        return jdbcTemplate.
                update("update villager set name = ?, profession_id = ?, village_id = ? where villager_id = ?",
                        villager.getName(), villager.getProfession().getId(), villager.getVillageId(),
                        villager.getId());
    }

    @Override
    public int delete(Villager villager) {
        return jdbcTemplate.update("delete from villager where village_id = ?", villager.getId());
    }

    @Override
    public List<Villager> getVillagersByVillage(int villageId) {
        String sql = "select * from villager where village_id = ?";

        return jdbcTemplate.query(sql, rowMapper, villageId);
    }
}
