package com.isbd.dto;

import com.isbd.model.AppliedEffect;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RaidDto {
    private final boolean isWin;
    private final AppliedEffect appliedEffect;
}
