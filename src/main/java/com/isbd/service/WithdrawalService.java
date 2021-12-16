package com.isbd.service;

import com.isbd.dto.WithdrawalDto;
import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.Withdrawal;
import com.isbd.repository.WithdrawalRepository;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawalService {
    private final WithdrawalRepository withdrawalRepository;
    private final AuthenticationFacade authenticationFacade;
    private final InventoryService inventoryService;

    public List<Withdrawal> getByPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        return withdrawalRepository.getByPlayer(playerId);
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
        if (withdrawalRepository.save(withdrawal) == 0) {
            throw new EntityNotSavedException(String.format("Вывод с идентификатором %d не сохранён",
                    withdrawal.getId()));
        }
    }
}
