package com.isbd.service.offer;

import com.isbd.Dao.OfferDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferDao offerDAO;

    @Override
    public List<Offer> getOffers() {
        return offerDAO.getAll();
    }

    @Override
    public Offer getOffer(long id) {
        return offerDAO.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Offer with id: %d was not found", id)));
    }

    // TODO добавить предмет продажи
    @Override
    public List<Offer> getOffers(Map<String, String> params) {
        Integer itemId = null;
        Integer amount = null;
        Integer villagerId = null;
        Integer reputationLevel = null;
        try {
            itemId = Integer.parseInt(params.get("itemId"));
        } catch (Exception ignored) {
        }
        try {
            amount = Integer.parseInt(params.get("amount"));
        } catch (Exception ignored) {
        }
        try {
            villagerId = Integer.parseInt(params.get("villagerId"));
        } catch (Exception ignored) {
        }
        try {
            reputationLevel = Integer.parseInt(params.get("reputationLevel"));
        } catch (Exception ignored) {
        }

        return getFilteredOffers(itemId, amount, villagerId, reputationLevel);
    }

    private List<Offer> getFilteredOffers(Integer itemId, Integer amount, Integer villagerId, Integer reputationLevel) {
        if (itemId != null && amount != null && villagerId != null && reputationLevel != null) {
            return offerDAO.getOffersByVillagerIdAndItemIdAndAmountAndReputationLevel(villagerId, itemId, amount,
                    reputationLevel);
        }
        if (itemId != null && amount != null && villagerId != null) {
            return offerDAO.getOffersByVillagerIdAndItemIdAndAmount(villagerId, itemId, amount);
        }
        if (itemId != null && amount != null && reputationLevel != null) {
            return offerDAO.getOffersByItemIdAndAmountAndReputationLevel(itemId, amount, reputationLevel);
        }
        if (itemId != null && amount != null) {
            return offerDAO.getOffersByItemIdAndAmount(itemId, amount);
        }
        if (itemId != null && reputationLevel != null) {
            return offerDAO.getOffersByItemIdAndReputationLevel(itemId, reputationLevel);
        }
        if (itemId != null && villagerId != null) {
            return offerDAO.getOffersByVillagerIdAndItemId(villagerId, itemId);
        }
        if (villagerId != null && reputationLevel != null) {
            return offerDAO.getOffersByVillagerIdAndReputationLevel(villagerId, reputationLevel);
        }
        if (itemId != null) {
            return offerDAO.getOffersByItemId(itemId);
        }
        if (villagerId != null) {
            return offerDAO.getOffersByVillagerId(villagerId);
        }
        if (reputationLevel != null) {
            return offerDAO.getOffersByReputationLevel(reputationLevel);
        }
        return null;
    }
}
