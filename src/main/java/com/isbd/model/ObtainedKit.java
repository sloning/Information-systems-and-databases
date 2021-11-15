package com.isbd.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ObtainedKit extends Kit {
    private LocalDateTime lastObtained;
}
