package com.isbd.service;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Deal;
import com.isbd.repository.DealRepository;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {
    private final DealRepository dealRepository;
    private final AuthenticationFacade authenticationFacade;
    private final OfferService offerService;

    public Deal getDeal(long id) {
        return dealRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Сделка с идентификатором %d не найдена", id)));
    }

    public List<Deal> getByPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        return dealRepository.getByPlayer(playerId);
    }

    public void createDeal(long offerId) {
        long playerId = authenticationFacade.getPlayerId();
        Deal deal = new Deal();
        deal.setPlayerId(playerId);
        deal.setOffer(offerService.getOffer(offerId));
        deal.setTime(LocalDateTime.now());
        dealRepository.save(deal);
    }
}
