package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Effect {
    private int id;
    private String name;
    private int power;
    private int duration;
}
