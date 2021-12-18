package com.isbd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kit {
    private int id;
    private String name;
    private long reload;
    private List<InventoryItem> items;
}
