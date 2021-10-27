package com.isbd.model;

import lombok.Data;
import org.postgresql.util.PGInterval;

import java.util.Date;

@Data
public class Effect {
    private final int id;
    private String name;
    private int power;
    private PGInterval duration;
    private Date startTime;
    private Date endTime;
}
