package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Villager {
    private int id;
    private String name;
    private Profession profession;
    private Village village;
}
