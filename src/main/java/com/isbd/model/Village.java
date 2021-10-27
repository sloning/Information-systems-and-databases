package com.isbd.model;

import lombok.Data;

@Data
public class Village {
    private final int id;
    private String name;
    private int xCoordinate;
    private int zCoordinate;
}
