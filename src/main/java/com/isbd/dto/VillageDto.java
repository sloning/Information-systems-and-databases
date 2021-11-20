package com.isbd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VillageDto {
    private int id;
    private String name;
    private int xCoordinate;
    private int zCoordinate;
    private boolean hasRaid;
}
