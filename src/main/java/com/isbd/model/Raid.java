package com.isbd.model;

import lombok.Data;

import java.util.Date;

@Data
public class Raid {
    private final int id;
    private Village village;
    private Date startTime;
    private Date endTime;
}
