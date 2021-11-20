package com.isbd.controller.api.v1;

import com.isbd.dto.RaidDto;
import com.isbd.model.Raid;
import com.isbd.service.raid.RaidService;
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
@RequestMapping(path = "/api/v1/raids",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RaidController {
    private final RaidService raidService;

    @GetMapping()
    public ResponseEntity<List<Raid>> getRaids() {
        return ResponseEntity.ok(raidService.getRaids());
    }

    @GetMapping("/{raidId}")
    public ResponseEntity<RaidDto> fightRaid(@PathVariable final int raidId) {
        return ResponseEntity.ok(raidService.fightRaid(raidId));
    }
}
