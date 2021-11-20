package com.isbd.repository;

import com.isbd.model.Player;

import java.util.Optional;

public interface PlayerRepository extends Repository<Player> {
    Optional<Player> getByUsername(String username);

    Optional<Player> getByUsernameAndPassword(String username, String password);
}
