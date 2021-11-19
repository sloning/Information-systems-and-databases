package com.isbd.controller.api.v1;

import com.isbd.model.Player;
import com.isbd.service.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/players",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping()
    public ResponseEntity<Player> getPlayer() {
        return ResponseEntity.ok(playerService.getPlayer());
    }
}
