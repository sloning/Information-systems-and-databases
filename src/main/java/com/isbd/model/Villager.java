package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Villager {
    private int id;
    private String name;
    private Profession profession;
    private Village village;
    private List<Offer> offers;
}
