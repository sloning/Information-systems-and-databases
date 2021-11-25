package com.isbd.repository;

import com.isbd.model.Profession;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfessionRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Profession> get(long id) {
        String sql = "select * from profession where profession_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToProfession), id);
    }

    public List<Profession> getAll() {
        String sql = "select * from profession";

        return jdbcTemplate.query(sql, this::mapRowToProfession);
    }

    public int save(Profession profession) {
        return jdbcTemplate.update("insert into profession(name) values(?)", profession.getName());
    }

    public int update(Profession profession) {
        return jdbcTemplate.
                update("update profession set name = ? where profession_id = ?",
                        profession.getName(), profession.getId());
    }

    public int delete(Profession profession) {
        return jdbcTemplate.update("delete from profession where profession_id = ?", profession.getId());
    }

    private Profession mapRowToProfession(ResultSet rs, int rowNum) throws SQLException {
        Profession profession = new Profession();
        profession.setId(rs.getInt("profession_id"));
        profession.setName(rs.getString("name"));
        return profession;
    }
}
