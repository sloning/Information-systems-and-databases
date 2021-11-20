package com.isbd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VillagerDto {
    private int villagerId;
    private String name;
    private String professionName;
    private int reputationLevelId;
    private String reputationLevel;
    private long offersAmount;
}
