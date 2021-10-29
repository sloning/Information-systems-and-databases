package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Village {
    private int id;
    private String name;
    private int xCoordinate;
    private int zCoordinate;
}
