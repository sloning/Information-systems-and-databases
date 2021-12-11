package com.isbd.repository;

import com.isbd.model.Biome;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BiomeRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Biome> get(int id) {
        String sql = "select * from biome where biome_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToBiome), id);
    }

    private Biome mapRowToBiome(ResultSet rs, int rowNum) throws SQLException {
        Biome biome = new Biome();
        biome.setId(rs.getInt("biome_id"));
        biome.setName(rs.getString("name"));
        return biome;
    }
}
