package com.isbd.dto;

import com.isbd.model.InventoryItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ObtainedKitDto {
    private int id;
    private String name;
    private List<InventoryItem> items;
    private long secondsBeforeObtainment;
}
