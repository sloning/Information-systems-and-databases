package com.isbd.service.offer;

import com.isbd.dto.OfferDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.AppliedEffect;
import com.isbd.model.InventoryItem;
import com.isbd.model.Offer;
import com.isbd.repository.OfferRepository;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.effect.AppliedEffectService;
import com.isbd.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final AppliedEffectService appliedEffectService;
    private final InventoryService inventoryService;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<Offer> getOffers(int limit, int offset) {
        return offerRepository.getAllWithPagination(offset, limit);
    }

    @Override
    public Offer getOffer(long id) {
        return offerRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Предложение с идентификатором %d не найден", id)));
    }

    // TODO добавить предмет продажи
    @Override
    public List<OfferDto> getOffers(Integer itemId, Integer amount, Integer villagerId, Integer reputationLevel,
                                    int limit, int offset) {
        List<Offer> offers = getFilteredOffers(itemId, amount, villagerId, reputationLevel, limit, offset);
        return convertToDtoList(offers);
    }

    @Override
    public long getAmountOfOffersByVillagerIdAndReputationLevel(int villagerId, int reputationLevel) {
        return offerRepository.getOffersByVillagerIdAndReputationLevel(villagerId, reputationLevel,
                Integer.MAX_VALUE, 0).size();
    }

    private List<Offer> getFilteredOffers(Integer itemId, Integer amount, Integer villagerId, Integer reputationLevel,
                                          int limit, int offset) {
        if (itemId != null && amount != null && villagerId != null && reputationLevel != null) {
            return offerRepository.getOffersByVillagerIdAndItemIdAndAmountAndReputationLevel(villagerId, itemId, amount,
                    reputationLevel, limit, offset);
        }
        if (itemId != null && amount != null && villagerId != null) {
            return offerRepository.getOffersByVillagerIdAndItemIdAndAmount(villagerId, itemId, amount, limit, offset);
        }
        if (itemId != null && amount != null && reputationLevel != null) {
            return offerRepository.getOffersByItemIdAndAmountAndReputationLevel(itemId, amount, reputationLevel, limit, offset);
        }
        if (itemId != null && amount != null) {
            return offerRepository.getOffersByItemIdAndAmount(itemId, amount, limit, offset);
        }
        if (itemId != null && reputationLevel != null) {
            return offerRepository.getOffersByItemIdAndReputationLevel(itemId, reputationLevel, limit, offset);
        }
        if (itemId != null && villagerId != null) {
            return offerRepository.getOffersByVillagerIdAndItemId(villagerId, itemId, limit, offset);
        }
        if (villagerId != null && reputationLevel != null) {
            return offerRepository.getOffersByVillagerIdAndReputationLevel(villagerId, reputationLevel, limit, offset);
        }
        if (itemId != null) {
            return offerRepository.getOffersByItemId(itemId, limit, offset);
        }
        if (villagerId != null) {
            return offerRepository.getOffersByVillagerId(villagerId, limit, offset);
        }
        if (reputationLevel != null) {
            return offerRepository.getOffersByReputationLevel(reputationLevel, limit, offset);
        }
        return null;
    }

    // TODO fabric?
    private List<OfferDto> convertToDtoList(List<Offer> offers) {
        List<OfferDto> offerDtos = new ArrayList<>();
        long playerId = authenticationFacade.getPlayerId();
        List<AppliedEffect> appliedEffects = appliedEffectService.getAppliedEffectsByPlayer(playerId);
        List<InventoryItem> items = inventoryService.getByPlayerId(playerId);
        boolean hasDiscount = false;
        for (AppliedEffect appliedEffect : appliedEffects) {
            if (appliedEffect.getId() == 1) {
                hasDiscount = true;
                break;
            }
        }

        for (Offer offer : offers) {
            OfferDto offerDto = new OfferDto();
            offerDto.setOfferId(offer.getId());
            offerDto.setBuyingItem(offer.getBuyingItem());
            offerDto.setSellingItem(offer.getSellingItem());
            offerDto.setAmountOfBuyingItems(offer.getAmountOfBuyingItems());
            offerDto.setAmountOfSellingItems(offer.getAmountOfSellingItems());
            if (hasDiscount) {
                offerDto.setHasDiscount(true);
                int amountOfBuyingItemsWithDiscount = offer.getAmountOfBuyingItems() / 2;
                offerDto.setAmountOfBuyingItemsWithDiscount(amountOfBuyingItemsWithDiscount);
            }

            for (InventoryItem item : items) {
                if (item.getId() == offer.getBuyingItem().getId()) {
                    if (hasDiscount)
                        offerDto.setCanBeTraded(offerDto.getAmountOfBuyingItemsWithDiscount() <= item.getAmount());
                    else offerDto.setCanBeTraded(offerDto.getAmountOfBuyingItems() <= item.getAmount());
                    if (offerDto.isCanBeTraded()) break;
                }
            }
            offerDtos.add(offerDto);
        }

        return offerDtos;
    }
}
