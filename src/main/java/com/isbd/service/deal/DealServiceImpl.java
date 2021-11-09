package com.isbd.service.deal;

import com.isbd.Dao.DealDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Deal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final DealDao dealDAO;

    @Override
    public Deal getDeal(long id) {
        return dealDAO.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Deal with id: %d was not found", id)));
    }

    @Override
    public List<Deal> getByPlayer(long playerId) {
        return dealDAO.getByPlayer(playerId);
    }

    @Override
    public void save(Deal deal) {
        dealDAO.save(deal);
    }
}
