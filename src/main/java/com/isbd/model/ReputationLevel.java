package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReputationLevel {
    private int id;
    private String name;
    private int neededReputationLevel;
}
