package com.isbd.service.kit;

import com.isbd.model.InventoryItem;
import com.isbd.model.ObtainedKit;

import java.util.List;

public interface KitService {
    List<ObtainedKit> getAll();

    List<InventoryItem> obtainKit(int kitId);
}
