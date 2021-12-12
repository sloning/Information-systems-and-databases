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
    private String reputationLevelName;
    private int currentReputation;
    private int dealsToNextReputationLevel;
    private long offersAmount;
}
