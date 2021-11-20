package com.isbd.repository;

import com.isbd.model.Village;

import java.util.List;
import java.util.Optional;

public interface VillageRepository {
    List<Village> getAll(int limit, int offset);

    Optional<Village> get(long id);

    int save(Village village);

    int update(Village village);

    int delete(Village village);
}
