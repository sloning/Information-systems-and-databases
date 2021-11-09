package com.isbd.Dao;

import com.isbd.model.Player;

import java.util.Optional;

public interface PlayerDao extends Dao<Player> {
    Optional<Player> getByUsername(String username);

    Optional<Player> getByUsernameAndPassword(String username, String password);
}
