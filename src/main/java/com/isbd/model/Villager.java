package com.isbd.model;

import lombok.Data;

import java.util.List;

@Data
public class Villager {
    private final int id;
    private String name;
    private Profession profession;
    private Village village;
    private List<Offer> offers;
}
