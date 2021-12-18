package com.isbd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReputationLevel {
    private int id;
    private String name;
    private int neededReputationLevel;
}
