package com.isbd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReputationDto {
    private int id;
    private String name;
    private int reputation;
}
