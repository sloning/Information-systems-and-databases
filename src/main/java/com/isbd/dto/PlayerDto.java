package com.isbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private long id;
    private String username;
    private int amountOfEmeralds;
    private int tradingExperience;
}
