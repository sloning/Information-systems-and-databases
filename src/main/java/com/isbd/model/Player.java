package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Player {
    private long id;
    private String username;
    private String password;
    private int tradingExperience;
}
