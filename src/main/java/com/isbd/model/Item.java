package com.isbd.model;

import lombok.Data;

import java.net.URL;

@Data
public class Item {
    private final int id;
    private String name;
    private URL iconAddress;
}
