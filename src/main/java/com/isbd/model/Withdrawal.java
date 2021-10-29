package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Withdrawal {
    private long id;
    private Village village;
    private Inventory inventory;
}
