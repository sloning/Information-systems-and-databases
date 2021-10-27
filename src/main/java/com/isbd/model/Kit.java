package com.isbd.model;

import lombok.Data;

import java.util.List;

@Data
public class Kit {
    private final int id;
    private String name;
    private List<Item> items;
}
