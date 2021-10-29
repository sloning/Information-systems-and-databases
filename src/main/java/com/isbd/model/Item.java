package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {
    private int id;
    private String name;
    private String iconAddress;
}
