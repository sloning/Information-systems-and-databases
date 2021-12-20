package com.isbd.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ObtainedKit extends Kit {
    private LocalDateTime lastObtained;

    public ObtainedKit() {
    }

    public ObtainedKit(int id, String name, int reload, List<InventoryItem> items, LocalDateTime lastObtained) {
        super(id, name, reload, items);
        this.lastObtained = lastObtained;
    }
}
