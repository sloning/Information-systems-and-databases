package com.isbd.service.withdrawal;

import com.isbd.DAO.WithdrawalDAO;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Withdrawal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {
    private final WithdrawalDAO withdrawalDAO;

    @Override
    public Withdrawal getWithdrawal(long id) {
        return withdrawalDAO.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Withdrawal with id: %d was not found", id)));
    }

    @Override
    public List<Withdrawal> getByPlayer(long id) {
        return withdrawalDAO.getByPlayer(id);
    }
}
