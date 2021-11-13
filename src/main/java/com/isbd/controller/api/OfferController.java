package com.isbd.controller.api;

import com.isbd.model.Offer;
import com.isbd.service.offer.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/offer",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OfferController {
    private final OfferService offerService;

    @GetMapping()
    public ResponseEntity<List<Offer>> getOffers() {
        return ResponseEntity.ok(offerService.getOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOffer(@PathVariable final long id) {
        return ResponseEntity.ok(offerService.getOffer(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Offer>> getFilteredOffers(@RequestParam final Map<String, String> params) {
        return ResponseEntity.ok(offerService.getOffers(params));
    }
}
