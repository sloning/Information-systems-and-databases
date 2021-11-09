package com.isbd.Dao;

import com.isbd.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlayerDaoImpl implements Dao<Player>, PlayerDao {
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

        return jdbcTemplate.update(sql, player.getUsername(), player.getPassword(), 0);
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

    @Override
    public Optional<Player> getByUsername(String username) {
        String sql = "select * from player where username = ?";

        List<Player> playerList = jdbcTemplate.query(sql, rowMapper, username);
        if (playerList.isEmpty()) return Optional.empty();
        else return Optional.ofNullable(playerList.get(0));
    }

    @Override
    public Optional<Player> getByUsernameAndPassword(String username, String password) {
        String sql = "select * from player where username = ? and password = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, username, password));
    }
}
