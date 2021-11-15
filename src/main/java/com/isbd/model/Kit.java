package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Kit {
    private int id;
    private String name;
    private List<InventoryItem> items;
}
