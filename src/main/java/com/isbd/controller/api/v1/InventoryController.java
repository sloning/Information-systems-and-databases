package com.isbd.controller.api.v1;

import com.isbd.model.InventoryItem;
import com.isbd.service.inventory.InventoryService;
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
@RequestMapping(path = "/api/v1/inventories",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{playerId}")
    public ResponseEntity<List<InventoryItem>> getInventory(@PathVariable final long playerId) {
        return ResponseEntity.ok(inventoryService.getByPlayerId(playerId));
    }
}
