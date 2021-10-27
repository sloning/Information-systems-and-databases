package com.isbd.model;

import lombok.Data;

@Data
public class ReputationLevel {
    private final int id;
    private String name;
    private int neededReputationLevel;
}
