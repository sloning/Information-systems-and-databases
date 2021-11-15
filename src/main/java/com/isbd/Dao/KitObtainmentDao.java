package com.isbd.Dao;

import com.isbd.model.ObtainedKit;

import java.util.List;
import java.util.Optional;

public interface KitObtainmentDao {
    List<ObtainedKit> getByKit(int kitId);

    List<ObtainedKit> getByPlayer(long playerId);

    Optional<ObtainedKit> getByPlayerAndKit(long playerId, int kitId);
}
