package com.isbd.repository;

import com.isbd.model.Kit;

public interface KitRepository extends Repository<Kit> {
    void obtainKit(long playerId, int kitId);
}
