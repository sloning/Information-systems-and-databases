package com.isbd.repository;

import com.isbd.model.Item;

import java.util.Optional;

public interface ItemRepository extends Repository<Item> {
    Optional<String> getIconAddress(int id);
}
