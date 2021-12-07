package com.isbd.controller.api.v1;

import com.isbd.model.Item;
import com.isbd.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/items",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
    private final ItemService itemService;

    @GetMapping()
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(itemService.getItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable final int id) {
        return ResponseEntity.ok(itemService.getItem(id));
    }
}
