package com.isbd.service;

import com.isbd.exception.EntityNotFoundException;
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

    public Withdrawal getWithdrawal(long id) {
        return withdrawalRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Вывод с идентификатором %d не найден", id)));
    }

    public List<Withdrawal> getByPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        return withdrawalRepository.getByPlayer(playerId);
    }

    public void createWithdrawal(int villageId) {
        long playerId = authenticationFacade.getPlayerId();
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setPlayerId(playerId);
        withdrawal.setVillageId(villageId);
        withdrawal.setItems(inventoryService.getByPlayerId(playerId));
        withdrawalRepository.save(withdrawal);
    }
}
