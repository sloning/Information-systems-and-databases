package com.isbd.service;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.repository.VillagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    private final VillagerRepository villagerRepository;

    public void validateVillager(int villagerId) {
        villagerRepository.get(villagerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Житель с идентификатором %d не найден", villagerId)));
    }
}
