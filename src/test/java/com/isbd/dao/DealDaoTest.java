package com.isbd.dao;

import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.Deal;
import com.isbd.model.Offer;
import com.isbd.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DealDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private OfferService offerService;
    private DealDao underTest;

    @BeforeEach
    void setUp() {
        underTest = new DealDao(jdbcTemplate, offerService);
    }

    @Test
    void shouldMakeNewDeal() {
        Deal deal = new Deal(1, 1, new Offer(), LocalDateTime.now());
        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

        int rowsUpdated = underTest.save(deal);

        assertThat(rowsUpdated).isGreaterThan(0);
    }

    @Test
    void shouldThrowException() {
        Deal deal = new Deal(1, 1, new Offer(), LocalDateTime.now());
        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenThrow(EntityNotSavedException.class);

        assertThatThrownBy(() -> underTest.save(deal)).isInstanceOf(EntityNotSavedException.class);
    }
}