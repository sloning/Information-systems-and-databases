package com.isbd.Dao;

import com.isbd.model.Item;

import java.util.Optional;

public interface ItemDao extends Dao<Item> {
    Optional<String> getIconAddress(int id);
}
