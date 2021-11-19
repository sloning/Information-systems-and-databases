package com.isbd.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Deal {
    private long id;
    private long playerId;
    private Offer offer;
    private LocalDateTime time;
}
