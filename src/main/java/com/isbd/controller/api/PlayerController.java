package com.isbd.controller.api;

import com.isbd.DTO.DealDTO;
import com.isbd.DTO.WithdrawalDTO;
import com.isbd.model.Player;
import com.isbd.service.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/player",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable final long id) {
        return new ResponseEntity<>(playerService.getPlayer(id), HttpStatus.OK);
    }

    @PostMapping("/withdrawal")
    public void makeNewWithdrawal(@RequestBody WithdrawalDTO withdrawalDTO) {
        playerService.makeNewWithdrawal(withdrawalDTO);
    }

    @PostMapping("/deal")
    public void makeNewDeal(@RequestBody DealDTO dealDTO) {
        playerService.makeNewDeal(dealDTO);
    }
}
