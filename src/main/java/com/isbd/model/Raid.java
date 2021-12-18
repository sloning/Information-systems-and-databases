package com.isbd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Raid {
    private int id;
    private int villageId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
