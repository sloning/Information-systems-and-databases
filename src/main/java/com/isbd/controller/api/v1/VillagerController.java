package com.isbd.controller.api.v1;

import com.isbd.model.Pageable;
import com.isbd.model.Villager;
import com.isbd.service.VillagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/villagers",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class VillagerController {
    private final VillagerService villagerService;

    @GetMapping()
    public ResponseEntity<List<Villager>> getVillagers(@RequestParam(defaultValue = "0") final int page,
                                                       @RequestParam(defaultValue = "25") final int size) {
        return ResponseEntity.ok(villagerService.getVillagers(new Pageable(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Villager> getVillager(@PathVariable final int id) {
        return ResponseEntity.ok(villagerService.getVillager(id));
    }
}
