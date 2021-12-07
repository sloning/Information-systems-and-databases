package com.isbd.service.mapper;

import com.isbd.dto.OfferAvailability;
import com.isbd.dto.OfferDto;
import com.isbd.model.AppliedEffect;
import com.isbd.model.InventoryItem;
import com.isbd.model.Offer;
import com.isbd.model.ReputationLevel;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.AppliedEffectService;
import com.isbd.service.InventoryService;
import com.isbd.service.ReputationLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OfferMapper {
    private final ReputationLevelService reputationLevelService;
    private final AuthenticationFacade authenticationFacade;
    private final AppliedEffectService appliedEffectService;
    private final InventoryService inventoryService;
    private boolean hasDiscount;
    private long playerId;

    public List<OfferDto> createFrom(List<Offer> entityList) {
        List<OfferDto> dtoList = new ArrayList<>();
        playerId = authenticationFacade.getPlayerId();
        List<AppliedEffect> appliedEffects = appliedEffectService.getAppliedEffectsByPlayer(playerId);
        hasDiscount = hasDiscount(appliedEffects);

        for (Offer entity : entityList) {
            OfferDto dto = new OfferDto();
            dto.setOfferId(entity.getId());
            dto.setBuyingItem(entity.getBuyingItem());
            dto.setSellingItem(entity.getSellingItem());
            dto.setAmountOfBuyingItems(entity.getAmountOfBuyingItems());
            dto.setAmountOfSellingItems(entity.getAmountOfSellingItems());
            if (hasDiscount) {
                dto.setHasDiscount(true);
                int amountOfBuyingItemsWithDiscount = entity.getAmountOfBuyingItems() / 2;
                dto.setAmountOfBuyingItemsWithDiscount(amountOfBuyingItemsWithDiscount);
            }
            dto.setOfferAvailability(getOfferAvailability(entity, dto));
            dtoList.add(dto);
        }

        return dtoList;
    }

    private boolean hasDiscount(List<AppliedEffect> appliedEffects) {
        for (AppliedEffect appliedEffect : appliedEffects) {
            if (appliedEffect.getId() == 1) {
                return true;
            }
        }
        return false;
    }

    private OfferAvailability getOfferAvailability(Offer entity, OfferDto dto) {
        ReputationLevel reputationLevel = reputationLevelService.getReputationLevelByVillagerId(entity.getVillagerId());
        if (hasEnoughReputationLevel(reputationLevel.getId(), entity.getReputationLevel().getId())) {
            return getOfferAvailabilityByItems(entity, dto);
        } else {
            return OfferAvailability.NOT_ENOUGH_REPUTATION;
        }
    }

    private boolean hasEnoughReputationLevel(int actualLevel, int requiredLevel) {
        return actualLevel >= requiredLevel;
    }

    private OfferAvailability getOfferAvailabilityByItems(Offer entity, OfferDto dto) {
        List<InventoryItem> items = inventoryService.getByPlayerId(playerId);
        for (InventoryItem item : items) {
            if (item.getId() == entity.getBuyingItem().getId()) {
                if (hasDiscount) {
                    if (canBeTraded(dto.getAmountOfBuyingItemsWithDiscount(), item.getAmount())) {
                        return OfferAvailability.AVAILABLE;
                    }
                } else {
                    if (canBeTraded(dto.getAmountOfBuyingItems(), item.getAmount())) {
                        return OfferAvailability.AVAILABLE;
                    }
                }
            }
        }
        return OfferAvailability.NOT_ENOUGH_ITEMS;
    }

    private boolean canBeTraded(int price, int available) {
        return price <= available;
    }
}
