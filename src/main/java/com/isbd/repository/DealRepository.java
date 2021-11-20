package com.isbd.repository;

import com.isbd.model.Deal;

import java.util.List;

public interface DealRepository extends Repository<Deal> {
    List<Deal> getByPlayer(long playerId);
}
