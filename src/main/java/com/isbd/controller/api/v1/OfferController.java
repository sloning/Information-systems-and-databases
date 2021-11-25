package com.isbd.controller.api.v1;

import com.isbd.dto.OfferDto;
import com.isbd.model.Offer;
import com.isbd.service.OfferService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Filter offers by buying item, amount, villager and reputation level.",
            notes = "Amount is less or equal, reputation level is less or equal. " +
                    "Example: ?itemId=1&amount=2&reputationLevel=1 " +
                    "will return offers with 2 or less items with id = 1 and reputation level less or equals 1.")
    @GetMapping("/filter")
    public ResponseEntity<List<OfferDto>> getFilteredOffers(@RequestParam(required = false) Integer itemId,
                                                            @RequestParam(required = false) Integer amount,
                                                            @RequestParam(required = false) Integer villagerId,
                                                            @RequestParam(required = false) Integer reputationLevel,
                                                            @RequestParam(defaultValue = "0") final int offset,
                                                            @RequestParam(defaultValue = "25") final int limit) {
        return ResponseEntity.ok(offerService.getOffers(itemId, amount, villagerId, reputationLevel, limit, offset));
    }
}
