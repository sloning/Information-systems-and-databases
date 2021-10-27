package com.isbd.model;

import lombok.Data;

import java.util.Map;

@Data
public class Withdrawal {
    private final long id;
    private Village village;
    private Map<Item, Integer> itemMap;
}
