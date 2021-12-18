package com.isbd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deal {
    private long id;
    private long playerId;
    private Offer offer;
    private LocalDateTime time;
}
