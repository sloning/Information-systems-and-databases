package com.isbd.DAO;

import com.isbd.model.Item;

public interface ItemDAO extends DAO<Item> {
    String getIconAddress(int id);
}
