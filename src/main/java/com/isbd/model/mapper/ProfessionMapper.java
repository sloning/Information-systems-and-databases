package com.isbd.model.mapper;

import com.isbd.model.Profession;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProfessionMapper implements RowMapper<Profession> {
    @Override
    public Profession mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profession profession = new Profession();
        profession.setName(rs.getString("name"));
        return profession;
    }
}
