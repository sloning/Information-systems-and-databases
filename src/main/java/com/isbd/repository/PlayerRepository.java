package com.isbd.repository;

import com.isbd.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlayerRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Player> get(long id) {
        String sql = "select * from player where player_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToPlayer), id);
    }

    public int save(Player player) {
        String sql = "insert into player(username, password, trading_experience) values(?, ?, ?)";

        return jdbcTemplate.update(sql, player.getUsername(), player.getPassword(), 0);
    }

    public int update(Player player) {
        String sql = "update player set username = ?, password = ?, trading_experience = ? where player_id = ?";

        return jdbcTemplate.update(sql, player.getUsername(), player.getPassword(), player.getTradingExperience(),
                player.getId());
    }

    public int delete(Player player) {
        String sql = "delete from player where player_id = ?";

        return jdbcTemplate.update(sql, player.getId());
    }

    public Optional<Player> getByUsername(String username) {
        String sql = "select * from player where username = ?";

        List<Player> playerList = jdbcTemplate.query(sql, this::mapRowToPlayer, username);
        if (playerList.isEmpty()) return Optional.empty();
        else return Optional.ofNullable(playerList.get(0));
    }

    private Player mapRowToPlayer(ResultSet rs, int rowNum) throws SQLException {
        long playerId = rs.getLong("player_id");

        Player player = new Player();
        player.setId(playerId);
        player.setUsername(rs.getString("username"));
        player.setPassword(rs.getString("password"));
        player.setTradingExperience(rs.getInt("trading_experience"));
        return player;
    }
}
