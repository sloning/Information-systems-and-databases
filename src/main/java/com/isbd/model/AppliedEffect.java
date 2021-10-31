package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AppliedEffect extends Effect {
    private long playerId;
    private Date startTime;
    private Date endTime;
}
