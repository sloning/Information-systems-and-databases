package com.isbd.service.player;

import com.isbd.DTO.DealDTO;
import com.isbd.DTO.WithdrawalDTO;
import com.isbd.model.Player;

public interface PlayerService {
    Player getPlayer(long id);

    void makeNewWithdrawal(WithdrawalDTO withdrawalDTO);

    void makeNewDeal(DealDTO dealDTO);
}
