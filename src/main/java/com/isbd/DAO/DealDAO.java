package com.isbd.DAO;

import com.isbd.model.Deal;

import java.util.List;

public interface DealDAO extends DAO<Deal> {
    List<Deal> getByPlayer(long playerId);
}
