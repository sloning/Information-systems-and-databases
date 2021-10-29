package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Deal {
    private int id;
    private Offer offer;
    private Date time;
}
