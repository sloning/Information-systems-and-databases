package com.isbd.service;

import com.isbd.dao.TradingReputationDao;
import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.TradingReputation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradingReputationServiceTest {
    @Mock
    private TradingReputationDao tradingReputationDao;
    private TradingReputationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TradingReputationService(tradingReputationDao);
    }

    @Test
    void shouldReturnTradingReputationByPlayerIdAndVillagerId() {
        TradingReputation tradingReputation = new TradingReputation(0, 0, 0);
        when(tradingReputationDao.getByPlayerAndVillager(anyLong(), anyInt())).thenReturn(Optional.of(tradingReputation));

        TradingReputation value = underTest.getTradingReputationByPlayerIdAndVillagerId(tradingReputation.getPlayerId(),
                tradingReputation.getVillagerId());

        assertThat(value).isEqualTo(tradingReputation);
    }

    @Test
    void shouldReturnNewTradingReputationWhenGotEmptyOptional() {
        TradingReputation tradingReputation = new TradingReputation(0, 0, 0);
        when(tradingReputationDao.getByPlayerAndVillager(anyLong(), anyInt())).thenReturn(Optional.empty());
        when(tradingReputationDao.save(any())).thenReturn(1);

        TradingReputation value = underTest.getTradingReputationByPlayerIdAndVillagerId(tradingReputation.getPlayerId(),
                tradingReputation.getVillagerId());

        assertThat(value).isEqualTo(tradingReputation);
        verify(tradingReputationDao).save(tradingReputation);
    }

    @Test
    void shouldSaveTradingReputation() {
        TradingReputation tradingReputation = new TradingReputation(0, 0, 0);
        when(tradingReputationDao.save(any())).thenReturn(1);

        underTest.save(tradingReputation);

        verify(tradingReputationDao).save(tradingReputation);
    }

    @Test
    void shouldThrowExceptionWhenCanNotSaveTradingReputation() {
        TradingReputation tradingReputation = new TradingReputation(0, 0, 0);
        when(tradingReputationDao.save(any())).thenReturn(0);

        assertThatThrownBy(() -> underTest.save(tradingReputation)).isInstanceOf(EntityNotSavedException.class);
    }
}