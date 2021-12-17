package com.isbd.service;

import com.isbd.dao.VillagerDao;
import com.isbd.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    private final VillagerDao villagerDao;

    public void validateVillager(int villagerId) {
        villagerDao.get(villagerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Житель с идентификатором %d не найден", villagerId)));
    }
}
