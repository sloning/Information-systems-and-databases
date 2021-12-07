package com.isbd.service.mapper;

import com.isbd.dto.ObtainedKitDto;
import com.isbd.model.ObtainedKit;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class ObtainedKitMapper {
    public ObtainedKitDto createFrom(ObtainedKit entity) {
        ObtainedKitDto dto = new ObtainedKitDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setItems(entity.getItems());
        dto.setSecondsBeforeObtainment(getSecondsBeforeObtainment(entity.getLastObtained()));

        return dto;
    }

    private long getSecondsBeforeObtainment(LocalDateTime lastObtained) {
        long secondsBeforeObtainment = ChronoUnit.SECONDS.between(lastObtained, LocalDateTime.now());
        if (secondsBeforeObtainment < 86400) {
            return secondsBeforeObtainment;
        } else {
            return -1;
        }
    }
}
