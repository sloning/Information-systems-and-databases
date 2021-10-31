package com.isbd.DAO;

import com.isbd.model.Withdrawal;

import java.util.List;

public interface WithdrawalDAO extends DAO<Withdrawal> {
    List<Withdrawal> getByPlayer(long playerId);
}
