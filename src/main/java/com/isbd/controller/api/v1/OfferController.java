package com.isbd.controller.api.v1;

import com.isbd.dto.OfferDto;
import com.isbd.model.Offer;
import com.isbd.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/offers",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OfferController {
    private final OfferService offerService;

    @GetMapping()
    public ResponseEntity<List<Offer>> getOffers(@RequestParam(defaultValue = "0") final int offset,
                                                 @RequestParam(defaultValue = "25") final int limit) {
        return ResponseEntity.ok(offerService.getOffers(offset, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOffer(@PathVariable final long id) {
        return ResponseEntity.ok(offerService.getOffer(id));
    }

    @GetMapping("/allowedOffers")
    public ResponseEntity<List<OfferDto>> getAllowedOffers(@RequestParam final int villagerId,
                                                           @RequestParam(defaultValue = "0") final int offset,
                                                           @RequestParam(defaultValue = "25") final int limit) {
        return ResponseEntity.ok(offerService.getAllowedOffers(villagerId, limit, offset));
    }

    @GetMapping("/allOffersByVillager")
    public ResponseEntity<List<OfferDto>> getAllOffersByVillager(@RequestParam final int villagerId,
                                                                 @RequestParam(defaultValue = "0") final int offset,
                                                                 @RequestParam(defaultValue = "25") final int limit) {
        return ResponseEntity.ok(offerService.getAllOffersByVillager(villagerId, limit, offset));
    }
}
