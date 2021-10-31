package com.isbd.DAO;

import com.isbd.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlayerDAOImpl implements DAO<Player> {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Player> rowMapper;

    @Override
    public Optional<Player> get(long id) {
        String sql = "select * from player where player_id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public List<Player> getAll() {
        // TODO implement
        return null;
    }

    @Override
    public int save(Player player) {
        String sql = "insert into player(username, password, trading_experience) values(?, ?, ?)";

        return jdbcTemplate.update(sql, player.getUsername(), player.getPassword(), player.getTradingExperience());
    }

    @Override
    public int update(Player player) {
        String sql = "update player set username = ?, password = ?, trading_experience = ? where player_id = ?";

        return jdbcTemplate.update(sql, player.getUsername(), player.getPassword(), player.getTradingExperience(),
                player.getId());
    }

    @Override
    public int delete(Player player) {
        String sql = "delete from player where player_id = ?";

        return jdbcTemplate.update(sql, player.getId());
    }
}
