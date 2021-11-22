package com.isbd.service.item;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Item;
import com.isbd.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        return itemRepository.getAll();
    }

    @Override
    public Item getItem(int id) {
        return itemRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Предмета с идентификатором %d не существует", id)));
    }

    public byte[] getIcon(int id) {
        String iconAddress = itemRepository.getIconAddress(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Предмета с идентификатором %d не существует", id)));
        File fi = new File(iconAddress);
        byte[] image;
        try {
            image = Files.readAllBytes(fi.toPath());
        } catch (Exception e) {
            throw new EntityNotFoundException(String.format("Иконка для предмета с идентификатором %d не найдена", id));
        }
        return image;
    }
}
