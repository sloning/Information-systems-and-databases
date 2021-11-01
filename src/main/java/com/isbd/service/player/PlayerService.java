package com.isbd.service.player;

import com.isbd.model.Player;

public interface PlayerService {
    Player getPlayer();

    void makeNewWithdrawal(int villageId);

    void makeNewDeal(long offerId);
}
