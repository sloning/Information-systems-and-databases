package com.isbd.service.withdrawal;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Withdrawal;
import com.isbd.repository.WithdrawalRepository;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {
    private final WithdrawalRepository withdrawalRepository;
    private final AuthenticationFacade authenticationFacade;
    private final InventoryService inventoryService;

    @Override
    public Withdrawal getWithdrawal(long id) {
        return withdrawalRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Вывод с идентификатором %d не найден", id)));
    }

    @Override
    public List<Withdrawal> getByPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        return withdrawalRepository.getByPlayer(playerId);
    }

    @Override
    public void createWithdrawal(int villageId) {
        long playerId = authenticationFacade.getPlayerId();
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setPlayerId(playerId);
        withdrawal.setVillageId(villageId);
        withdrawal.setItems(inventoryService.getByPlayerId(playerId));
        withdrawalRepository.save(withdrawal);
    }
}
