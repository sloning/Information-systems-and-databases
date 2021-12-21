package com.isbd.dto;

import com.isbd.model.Biome;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VillageDto {
    private int id;
    private String name;
    private int xCoordinate;
    private int zCoordinate;
    private Biome biome;
    private boolean hasRaid;
    private int raidId;
}
