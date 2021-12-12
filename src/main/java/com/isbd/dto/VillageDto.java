package com.isbd.dto;

import com.isbd.model.Biome;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VillageDto {
    private int id;
    private String name;
    private int xCoordinate;
    private int zCoordinate;
    private Biome biome;
    private boolean hasRaid;
    private int raidId;
}
