package com.isbd.service;

import com.isbd.dao.ItemDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemDao itemDao;

    public List<Item> getItems() {
        return itemDao.getAll();
    }

    public Item getItem(int id) {
        return itemDao.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Предмета с идентификатором %d не существует", id)));
    }
}
