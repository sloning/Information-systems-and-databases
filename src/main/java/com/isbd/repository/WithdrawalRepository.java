package com.isbd.repository;

import com.isbd.model.Withdrawal;

import java.util.List;

public interface WithdrawalRepository extends Repository<Withdrawal> {
    List<Withdrawal> getByPlayer(long playerId);
}
