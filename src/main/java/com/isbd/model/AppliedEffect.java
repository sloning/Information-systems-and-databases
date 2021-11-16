package com.isbd.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AppliedEffect extends Effect {
    private long playerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
