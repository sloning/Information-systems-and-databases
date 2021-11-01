package com.isbd.controller.api;

import com.isbd.model.Player;
import com.isbd.service.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/player",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {
    private final PlayerService playerService;

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
}
