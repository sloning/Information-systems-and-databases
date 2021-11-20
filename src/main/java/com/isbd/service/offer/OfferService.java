package com.isbd.service.offer;

import com.isbd.dto.OfferDto;
import com.isbd.model.Offer;

import java.util.List;

public interface OfferService {
    List<Offer> getOffers(int limit, int offset);

    Offer getOffer(long id);

    List<OfferDto> getOffers(Integer itemId, Integer amount, Integer villagerId, Integer reputationLevel,
                             int limit, int offset);

    long getAmountOfOffersByVillagerIdAndReputationLevel(int villagerId, int reputationLevel);
}
