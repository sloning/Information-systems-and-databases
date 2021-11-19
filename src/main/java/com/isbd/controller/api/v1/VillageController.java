package com.isbd.controller.api.v1;

import com.isbd.Dto.VillageDto;
import com.isbd.Dto.VillagerDto;
import com.isbd.model.Village;
import com.isbd.model.Villager;
import com.isbd.service.village.VillageService;
import com.isbd.service.villager.VillagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/villages",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class VillageController {
    private final VillageService villageService;
    private final VillagerService villagerService;

    @GetMapping()
    public ResponseEntity<List<Village>> getVillages(@RequestParam(defaultValue = "25") final int limit,
                                                     @RequestParam(defaultValue = "0") final int offset) {
        return ResponseEntity.ok(villageService.getVillages(limit, offset));
    }

    @GetMapping("/villagesWithExtraData")
    public ResponseEntity<List<VillageDto>> getVillagesWithExtraData(@RequestParam(defaultValue = "25") final int limit,
                                                                     @RequestParam(defaultValue = "0") final int offset) {
        return ResponseEntity.ok(villageService.getVillagesWithExtraData(limit, offset));
    }

    @GetMapping("/{villageId}")
    public ResponseEntity<Village> getVillage(@PathVariable final int villageId) {
        return ResponseEntity.ok(villageService.getVillage(villageId));
    }

    @GetMapping("/{villageId}/villagers")
    public ResponseEntity<List<Villager>> getVillagersOfVillage(@PathVariable final int villageId,
                                                                @RequestParam(defaultValue = "0") final int offset,
                                                                @RequestParam(defaultValue = "25") final int limit) {
        return ResponseEntity.ok(villagerService.getVillagersOfVillage(villageId, offset, limit));
    }

    @GetMapping("/{villageId}/villagersWithExtraData")
    public ResponseEntity<List<VillagerDto>> villagersWithExtraData(@PathVariable final int villageId,
                                                                    @RequestParam(defaultValue = "0") final int offset,
                                                                    @RequestParam(defaultValue = "25") final int limit) {
        return ResponseEntity.ok(villagerService.getVillagersWithExtraData(villageId, offset, limit));
    }

    @GetMapping("/nearest")
    public ResponseEntity<Village> getNearestVillage(@RequestParam final int xCoordinate,
                                                     @RequestParam final int zCoordinate) {
        return ResponseEntity.ok(villageService.getNearestVillage(xCoordinate, zCoordinate));
    }
}
