package com.isbd.Dao;

import com.isbd.model.Item;

public interface ItemDao extends Dao<Item> {
    String getIconAddress(int id);
}
