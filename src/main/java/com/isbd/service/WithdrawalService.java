package com.isbd.service;

import com.isbd.dao.WithdrawalDao;
import com.isbd.dto.WithdrawalDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.Withdrawal;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawalService {
    private final WithdrawalDao withdrawalDao;
    private final AuthenticationFacade authenticationFacade;
    private final InventoryService inventoryService;

    public List<Withdrawal> getByPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        List<Withdrawal> withdrawals = withdrawalDao.getByPlayer(playerId);
        if (withdrawals.isEmpty()) {
            throw new EntityNotFoundException(String.format("Игрок %d ещё не совершил ни одного вывода.", playerId));
        }
        return withdrawals;
    }

    public void createWithdrawal(WithdrawalDto withdrawalDto) {
        long playerId = authenticationFacade.getPlayerId();
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setPlayerId(playerId);
        withdrawal.setVillageId(withdrawalDto.getVillageId());
        withdrawal.setItems(inventoryService.getByPlayerId(playerId));
        save(withdrawal);
    }

    public void save(Withdrawal withdrawal) {
        if (withdrawalDao.save(withdrawal) == 0) {
            throw new EntityNotSavedException(String.format("Вывод с идентификатором %d не сохранён",
                    withdrawal.getId()));
        }
    }
}
