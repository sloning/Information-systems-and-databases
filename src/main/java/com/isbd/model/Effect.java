package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgresql.util.PGInterval;

import java.util.Date;

@Data
@NoArgsConstructor
public class Effect {
    private int id;
    private String name;
    private int power;
    private PGInterval duration;
    private Date startTime;
    private Date endTime;
}
