package com.isbd.security.jwt;

import com.isbd.model.Player;

public class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(Player player) {
        return new JwtUser(player.getId(), player.getUsername(), player.getPassword());
    }

    public static JwtUser create(String id) {
        long playerId = Long.parseLong(id);
        return new JwtUser(playerId, null, null);
    }
}
