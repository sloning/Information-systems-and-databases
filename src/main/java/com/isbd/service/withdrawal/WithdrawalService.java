package com.isbd.service.withdrawal;

import com.isbd.model.Withdrawal;

import java.util.List;

public interface WithdrawalService {
    Withdrawal getWithdrawal(long id);

    List<Withdrawal> getByPlayer();

    void createWithdrawal(int villageId);
}
