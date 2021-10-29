package com.isbd.service.item;

import com.isbd.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItems();

    Item getItem(int id);

    byte[] getIcon(int id);
}
