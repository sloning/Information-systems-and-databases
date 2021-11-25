package com.isbd.controller.api.v1;

import com.isbd.model.AppliedEffect;
import com.isbd.service.AppliedEffectService;
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
@RequestMapping(path = "/api/v1/appliedeffects",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AppliedEffectController {
    private final AppliedEffectService appliedEffectService;

    @GetMapping("/{playerId}")
    public ResponseEntity<List<AppliedEffect>> getAppliedEffects(@PathVariable final long playerId) {
        return ResponseEntity.ok(appliedEffectService.getAppliedEffectsByPlayer(playerId));
    }
}
