package com.isbd.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AppliedEffect extends Effect {
    private long playerId;
    private Date startTime;
    private Date endTime;
}
