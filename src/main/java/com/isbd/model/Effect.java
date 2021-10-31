package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgresql.util.PGInterval;

@Data
@NoArgsConstructor
public class Effect {
    private int id;
    private String name;
    private int power;
    private PGInterval duration;
}
