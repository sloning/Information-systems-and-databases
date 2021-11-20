package com.isbd.service.kit;

import com.isbd.model.InventoryItem;
import com.isbd.model.Kit;
import com.isbd.model.ObtainedKit;

import java.util.List;

public interface KitService {
    Kit getKit(int kitId);

    List<ObtainedKit> getAll();

    List<InventoryItem> obtainKit(int kitId);
}
