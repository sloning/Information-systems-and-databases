package com.isbd.service.offer;

import com.isbd.model.Offer;

import java.util.List;
import java.util.Map;

public interface OfferService {
    List<Offer> getOffers();

    Offer getOffer(long id);

    List<Offer> getOffers(Map<String, String> params);

}
