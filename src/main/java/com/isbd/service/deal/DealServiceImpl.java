package com.isbd.service.deal;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Deal;
import com.isbd.repository.DealRepository;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.offer.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final AuthenticationFacade authenticationFacade;
    private final OfferService offerService;

    @Override
    public Deal getDeal(long id) {
        return dealRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Deal with id: %d was not found", id)));
    }

    @Override
    public List<Deal> getByPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        return dealRepository.getByPlayer(playerId);
    }

    @Override
    public void createDeal(long offerId) {
        long playerId = authenticationFacade.getPlayerId();
        Deal deal = new Deal();
        deal.setPlayerId(playerId);
        deal.setOffer(offerService.getOffer(offerId));
        deal.setTime(LocalDateTime.now());
        dealRepository.save(deal);
    }
}
