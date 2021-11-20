package com.isbd.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> get(long id);

    List<T> getAll();

    int save(T t);

    int update(T t);

    int delete(T t);
}
