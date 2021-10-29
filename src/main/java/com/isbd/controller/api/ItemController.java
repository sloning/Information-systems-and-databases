package com.isbd.controller.api;

import com.isbd.model.Item;
import com.isbd.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/item",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
    private final ItemService itemService;

    @GetMapping()
    public ResponseEntity<List<Item>> getItems() {
        return new ResponseEntity<>(itemService.getItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable final int id) {
        return new ResponseEntity<>(itemService.getItem(id), HttpStatus.OK);
    }

    @GetMapping(
            value = "/icon/{id}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody
    byte[] getIcon(@PathVariable final int id) {
        return itemService.getIcon(id);
    }
}
