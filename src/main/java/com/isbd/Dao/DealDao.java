package com.isbd.Dao;

import com.isbd.model.Deal;

import java.util.List;

public interface DealDao extends Dao<Deal> {
    List<Deal> getByPlayer(long playerId);
}
