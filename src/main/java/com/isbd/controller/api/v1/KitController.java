package com.isbd.controller.api.v1;

import com.isbd.model.InventoryItem;
import com.isbd.model.ObtainedKit;
import com.isbd.service.KitService;
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
@RequestMapping(path = "/api/v1/kits",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class KitController {
    private final KitService kitService;

    @GetMapping()
    public ResponseEntity<List<ObtainedKit>> getKitsList() {
        return ResponseEntity.ok(kitService.getAll());
    }

    @GetMapping("/{kitId}")
    public ResponseEntity<List<InventoryItem>> obtainKit(@PathVariable final int kitId) {
        return ResponseEntity.ok(kitService.obtainKit(kitId));
    }
}
