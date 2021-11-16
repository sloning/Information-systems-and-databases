package com.isbd.controller.api;

import com.isbd.Dto.RaidDto;
import com.isbd.model.InventoryItem;
import com.isbd.model.ObtainedKit;
import com.isbd.model.Player;
import com.isbd.model.Raid;
import com.isbd.service.player.PlayerService;
import com.isbd.service.raid.RaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/player",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {
    private final PlayerService playerService;
    private final RaidService raidService;

    @GetMapping()
    public ResponseEntity<Player> getPlayer() {
        return ResponseEntity.ok(playerService.getPlayer());
    }

    @PostMapping("/withdrawal")
    public void makeNewWithdrawal(@RequestBody final int villageId) {
        playerService.makeNewWithdrawal(villageId);
    }

    @PostMapping("/deal")
    public void makeNewDeal(@RequestBody final long offerId) {
        playerService.makeNewDeal(offerId);
    }

    @GetMapping("/kit/{kitId}")
    public ResponseEntity<List<InventoryItem>> obtainKit(@PathVariable final int kitId) {
        return ResponseEntity.ok(playerService.obtainKit(kitId));
    }

    @GetMapping("/kit")
    public ResponseEntity<List<ObtainedKit>> getLastObtainedKits() {
        return ResponseEntity.ok(playerService.getKits());
    }

    @GetMapping("/raid")
    public ResponseEntity<List<Raid>> getRaids() {
        return ResponseEntity.ok(raidService.getRaids());
    }

    @GetMapping("/raid/{raidId}")
    public ResponseEntity<RaidDto> fightRaid(@PathVariable final int raidId) {
        return ResponseEntity.ok(playerService.fightRaid(raidId));
    }
}
