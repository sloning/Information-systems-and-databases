package com.isbd.Dao;

import com.isbd.model.Kit;

public interface KitDao extends Dao<Kit> {
    void obtainKit(long playerId, int kitId);
}
