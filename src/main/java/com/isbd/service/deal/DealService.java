package com.isbd.service.deal;

import com.isbd.model.Deal;

import java.util.List;

public interface DealService {
    Deal getDeal(long id);

    List<Deal> getByPlayer(long id);

    void save(Deal deal);
}
