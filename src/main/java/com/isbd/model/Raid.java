package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Raid {
    private int id;
    private Village village;
    private Date startTime;
    private Date endTime;
}
