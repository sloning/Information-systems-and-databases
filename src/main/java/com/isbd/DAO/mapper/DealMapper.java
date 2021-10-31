package com.isbd.DAO.mapper;

import com.isbd.DAO.OfferDAO;
import com.isbd.model.Deal;
import com.isbd.model.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DealMapper implements RowMapper<Deal> {
    private final OfferDAO offerDAO;

    @Override
    public Deal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = offerDAO.get(rs.getLong("offer_id")).get();

        Deal deal = new Deal();
        deal.setId(rs.getLong("deal_id"));
        deal.setOffer(offer);
        deal.setTime(rs.getDate("time"));
        deal.setPlayerId(rs.getLong("player_id"));
        return deal;
    }
}
