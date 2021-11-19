package com.isbd.controller.api.v1;

import com.isbd.model.Item;
import com.isbd.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(
            value = "/{id}/icon",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody
    byte[] getIcon(@PathVariable final int id) {
        return itemService.getIcon(id);
    }
}
