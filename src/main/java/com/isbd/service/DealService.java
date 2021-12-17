package com.isbd.service;

import com.isbd.dao.DealDao;
import com.isbd.dto.DealDto;
import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.Deal;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {
    private final DealDao dealDao;
    private final AuthenticationFacade authenticationFacade;
    private final OfferService offerService;

    public List<Deal> getByPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        return dealDao.getByPlayer(playerId);
    }

    public void createDeal(DealDto dealDto) {
        long playerId = authenticationFacade.getPlayerId();
        Deal deal = new Deal();
        deal.setPlayerId(playerId);
        deal.setOffer(offerService.getOffer(dealDto.getOfferId()));
        deal.setTime(LocalDateTime.now());
        dealDao.save(deal);
    }

    public void save(Deal deal) {
        if (dealDao.save(deal) == 0) {
            throw new EntityNotSavedException(String.format("Сделка с идентиффикатором %d не сохранена", deal.getId()));
        }
    }
}
