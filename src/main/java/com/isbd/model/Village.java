package com.isbd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Village {
    private int id;
    private String name;
    private int xCoordinate;
    private int zCoordinate;
    private Biome biome;
}
