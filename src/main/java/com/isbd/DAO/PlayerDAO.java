package com.isbd.DAO;

import com.isbd.model.Player;

import java.util.Optional;

public interface PlayerDAO extends DAO<Player> {
    Optional<Player> getByUsername(String username);

    Optional<Player> getByUsernameAndPassword(String username, String password);
}
