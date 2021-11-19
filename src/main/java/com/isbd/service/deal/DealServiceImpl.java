package com.isbd.service.deal;

import com.isbd.Dao.DealDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Deal;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.offer.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final DealDao dealDAO;
    private final AuthenticationFacade authenticationFacade;
    private final OfferService offerService;

    @Override
    public Deal getDeal(long id) {
        return dealDAO.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Deal with id: %d was not found", id)));
    }

    @Override
    public List<Deal> getByPlayer() {
        long playerId = authenticationFacade.getPlayerId();
        return dealDAO.getByPlayer(playerId);
    }

    @Override
    public void createDeal(long offerId) {
        long playerId = authenticationFacade.getPlayerId();
        Deal deal = new Deal();
        deal.setPlayerId(playerId);
        deal.setOffer(offerService.getOffer(offerId));
        deal.setTime(LocalDateTime.now());
        dealDAO.save(deal);
    }
}
