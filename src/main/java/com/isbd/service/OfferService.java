package com.isbd.service;

import com.isbd.dao.OfferDao;
import com.isbd.dto.OfferDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Offer;
import com.isbd.model.Pageable;
import com.isbd.model.ReputationLevel;
import com.isbd.service.mapper.OfferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final ReputationLevelService reputationLevelService;
    private final Validator validator;
    private final OfferDao offerDao;
    private final OfferMapper offerMapper;

    public List<Offer> getOffers(Pageable pageable) {
        return offerDao.getAll(pageable);
    }

    public Offer getOffer(long id) {
        return offerDao.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Предложение с идентификатором %d не найден", id)));
    }

    public List<OfferDto> getAllowedOffers(int villagerId, Pageable pageable) {
        validator.validateVillager(villagerId);
        ReputationLevel reputationLevel = reputationLevelService.getReputationLevelByVillagerId(villagerId);
        List<Offer> offers = offerDao.getOffersByVillagerIdAndReputationLevel(villagerId, reputationLevel.getId(),
                pageable);
        return offerMapper.createFrom(offers);
    }

    public List<OfferDto> getAllOffersByVillager(int villagerId, Pageable pageable) {
        List<Offer> offers = offerDao.getOffersByVillagerId(villagerId, pageable);
        return offerMapper.createFrom(offers);
    }

    public long getAmountOfOffersByVillagerIdAndReputationLevel(int villagerId, int reputationLevel) {
        return offerDao.getOffersByVillagerIdAndReputationLevel(villagerId, reputationLevel,
                Pageable.all()).size();
    }
}
