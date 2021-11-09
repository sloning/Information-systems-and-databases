package com.isbd.Dao;

import com.isbd.model.Withdrawal;

import java.util.List;

public interface WithdrawalDao extends Dao<Withdrawal> {
    List<Withdrawal> getByPlayer(long playerId);
}
