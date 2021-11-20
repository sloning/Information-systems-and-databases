package com.isbd.dto;

import com.isbd.model.AppliedEffect;
import lombok.Data;

@Data
public class RaidDto {
    private final boolean isWin;
    private final AppliedEffect appliedEffect;
}
