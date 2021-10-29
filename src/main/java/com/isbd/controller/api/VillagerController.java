package com.isbd.controller.api;

import com.isbd.model.Villager;
import com.isbd.service.villager.VillagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/villager",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class VillagerController {
    private final VillagerService villagerService;

    @GetMapping()
    public ResponseEntity<List<Villager>> getVillagers() {
        return new ResponseEntity<>(villagerService.getVillagers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Villager> getVillager(@PathVariable final int id) {
        return new ResponseEntity<>(villagerService.getVillager(id), HttpStatus.OK);
    }

    @GetMapping("/village/{villageId}")
    public ResponseEntity<List<Villager>> getVillagersOfVillage(@PathVariable final int villageId) {
        return new ResponseEntity<>(villagerService.getVillagersOfVillage(villageId), HttpStatus.OK);
    }
}
