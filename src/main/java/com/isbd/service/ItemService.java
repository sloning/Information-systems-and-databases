package com.isbd.service;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Item;
import com.isbd.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> getItems() {
        return itemRepository.getAll();
    }

    public Item getItem(int id) {
        return itemRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Предмета с идентификатором %d не существует", id)));
    }
}
