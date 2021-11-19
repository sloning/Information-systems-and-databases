package com.isbd.model.mapper;

import com.isbd.model.Deal;
import com.isbd.model.Offer;
import com.isbd.service.offer.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DealMapper implements RowMapper<Deal> {
    private final OfferService offerService;

    @Override
    public Deal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = offerService.getOffer(rs.getLong("offer_id"));

        Deal deal = new Deal();
        deal.setId(rs.getLong("deal_id"));
        deal.setOffer(offer);
        deal.setTime(rs.getTimestamp("time").toLocalDateTime());
        deal.setPlayerId(rs.getLong("player_id"));
        return deal;
    }
}
