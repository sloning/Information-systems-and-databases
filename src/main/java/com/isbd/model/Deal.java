package com.isbd.model;

import lombok.Data;

import java.util.Date;

@Data
public class Deal {
    private final int id;
    private Offer offer;
    private Date time;
}
