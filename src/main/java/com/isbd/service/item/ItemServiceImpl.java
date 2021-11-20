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
                new EntityNotFoundException(String.format("Item with id: %d was not found", id)));
    }

    public byte[] getIcon(int id) {
        String iconAddress = itemRepository.getIconAddress(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Item with id: %d not found or has not icon", id)));
        File fi = new File(iconAddress);
        byte[] image;
        try {
            image = Files.readAllBytes(fi.toPath());
        } catch (Exception e) {
            throw new EntityNotFoundException(String.format("Icon for item with id: %d was not found", id));
        }
        return image;
    }
}
