package com.isbd.controller.api.v1;

import com.isbd.dto.ReputationDto;
import com.isbd.service.ReputationLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/reputations",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ReputationController {
    private final ReputationLevelService reputationLevelService;

    @GetMapping("/{villagerId}")
    public ResponseEntity<ReputationDto> getReputationByVillagerId(@PathVariable final int villagerId) {
        return ResponseEntity.ok(reputationLevelService.getReputationByVillagerId(villagerId));
    }
}
