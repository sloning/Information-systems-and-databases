package com.isbd.service.item;

import com.isbd.Dao.ItemDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDAO;

    @Override
    public List<Item> getItems() {
        return itemDAO.getAll();
    }

    @Override
    public Item getItem(int id) {
        return itemDAO.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Item with id: %d was not found", id)));
    }

    public byte[] getIcon(int id) {
        String icon_address = itemDAO.getIconAddress(id);
        File fi = new File(icon_address);
        byte[] image;
        try {
            image = Files.readAllBytes(fi.toPath());
        } catch (Exception e) {
            throw new EntityNotFoundException(String.format("Icon for item with id: %d was not found", id));
        }
        return image;
    }
}
