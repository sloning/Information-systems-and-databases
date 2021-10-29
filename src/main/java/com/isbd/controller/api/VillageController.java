package com.isbd.controller.api;

import com.isbd.model.Village;
import com.isbd.service.village.VillageService;
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
@RequestMapping(path = "/api/village",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class VillageController {
    private final VillageService villageService;

    @GetMapping()
    public ResponseEntity<List<Village>> getVillages() {
        return new ResponseEntity<>(villageService.getVillages(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Village> getVillage(@PathVariable int id) {
        return new ResponseEntity<>(villageService.getVillage(id), HttpStatus.OK);
    }

    @GetMapping("/?x_coordinate={xCoordinate}&z_coordinate={zCoordinate}")
    public ResponseEntity<Village> getNearestVillage(@PathVariable int xCoordinate, @PathVariable int zCoordinate) {
        return new ResponseEntity<>(villageService.getNearestVillage(xCoordinate, zCoordinate), HttpStatus.OK);
    }
}
