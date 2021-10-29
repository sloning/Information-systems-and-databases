package com.isbd.DAO.mapper;

import com.isbd.model.ReputationLevel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReputationLevelMapper implements RowMapper<ReputationLevel> {
    @Override
    public ReputationLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReputationLevel reputationLevel = new ReputationLevel();
        reputationLevel.setId(rs.getInt("reputation_level_id"));
        reputationLevel.setName(rs.getString("name"));
        reputationLevel.setNeededReputationLevel(rs.getInt("needed_reputation"));
        return reputationLevel;
    }
}
