package com.isbd.controller.api.v1;

import com.isbd.model.Deal;
import com.isbd.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/deals",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class DealController {
    private final DealService dealService;

    @GetMapping
    public ResponseEntity<List<Deal>> getDeals() {
        return ResponseEntity.ok(dealService.getByPlayer());
    }

    @PostMapping()
    public void makeNewDeal(@RequestBody final long offerId) {
        dealService.createDeal(offerId);
    }
}
