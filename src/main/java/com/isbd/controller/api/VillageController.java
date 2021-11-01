package com.isbd.controller.api;

import com.isbd.model.Village;
import com.isbd.service.village.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/village",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class VillageController {
    private final VillageService villageService;

    @GetMapping()
    public ResponseEntity<List<Village>> getVillages() {
        return ResponseEntity.ok(villageService.getVillages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Village> getVillage(@PathVariable final int id) {
        return ResponseEntity.ok(villageService.getVillage(id));
    }

    @GetMapping("/nearest")
    public ResponseEntity<Village> getNearestVillage(@RequestParam final int xCoordinate,
                                                     @RequestParam final int zCoordinate) {
        return ResponseEntity.ok(villageService.getNearestVillage(xCoordinate, zCoordinate));
    }
}
