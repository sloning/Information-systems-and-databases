package com.isbd.service.player;

import com.isbd.model.InventoryItem;
import com.isbd.model.ObtainedKit;
import com.isbd.model.Player;

import java.util.List;

public interface PlayerService {
    Player getPlayer();

    void makeNewWithdrawal(int villageId);

    void makeNewDeal(long offerId);

    List<InventoryItem> obtainKit(int kitId);

    List<ObtainedKit> getKits();
}
