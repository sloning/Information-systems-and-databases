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
        dto.setSecondsBeforeObtainment(getSecondsBeforeObtainment(entity));

        return dto;
    }

    private long getSecondsBeforeObtainment(ObtainedKit entity) {
        long secondsToReload = entity.getReload();
        long secondsAfterObtainment = ChronoUnit.SECONDS.between(entity.getLastObtained(), LocalDateTime.now());
        if (secondsAfterObtainment < secondsToReload) {
            return secondsToReload - secondsAfterObtainment;
        } else {
            return -1;
        }
    }
}
