package com.isbd.service;

import com.isbd.dao.ReputationLevelDao;
import com.isbd.dto.ReputationDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.ReputationLevel;
import com.isbd.model.TradingReputation;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.mapper.ReputationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReputationLevelServiceTest {
    @Mock
    private ReputationLevelDao reputationLevelDao;
    @Mock
    private TradingReputationService tradingReputationService;
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private ReputationMapper reputationMapper;
    private ReputationLevelService underTest;

    private static Stream<TradingReputation> provideTradingReputation() {
        return Stream.of(
                new TradingReputation(0, 0, 0),
                new TradingReputation(0, 0, 10),
                new TradingReputation(0, 0, 70),
                new TradingReputation(0, 0, 150),
                new TradingReputation(0, 0, 250)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new ReputationLevelService(reputationLevelDao, tradingReputationService, authenticationFacade,
                reputationMapper);
    }

    @ParameterizedTest
    @MethodSource("provideTradingReputation")
    public void shouldReturnReputationLevelByTradingReputation(TradingReputation tradingReputation) {
        List<ReputationLevel> reputationLevels = List.of(
                new ReputationLevel(0, "name0", 0),
                new ReputationLevel(1, "name1", 10),
                new ReputationLevel(2, "name2", 70),
                new ReputationLevel(3, "name3", 150),
                new ReputationLevel(4, "name4", 250)
        );
        when(reputationLevelDao.getAll()).thenReturn(reputationLevels);

        ReputationLevel value = underTest.getReputationLevelByTradingReputation(tradingReputation);

        assertThat(value.getNeededReputationLevel()).isLessThanOrEqualTo(tradingReputation.getReputation());
    }

    @Test
    public void shouldThrowExceptionWhenGotEmptyList() {
        TradingReputation tradingReputation = new TradingReputation(0, 0, 0);
        List<ReputationLevel> reputationLevels = new ArrayList<>();
        when(reputationLevelDao.getAll()).thenReturn(reputationLevels);

        assertThatThrownBy(() -> underTest.getReputationLevelByTradingReputation(tradingReputation))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void shouldReturnReputationLevelByVillagerId() {
        List<ReputationLevel> reputationLevels = List.of(
                new ReputationLevel(0, "name0", 0),
                new ReputationLevel(1, "name1", 10),
                new ReputationLevel(2, "name2", 70),
                new ReputationLevel(3, "name3", 150),
                new ReputationLevel(4, "name4", 250)
        );
        when(authenticationFacade.getPlayerId()).thenReturn(0L);
        when(tradingReputationService.getTradingReputationByPlayerIdAndVillagerId(0, 0))
                .thenReturn(new TradingReputation(0, 0, 0));
        when(reputationLevelDao.getAll()).thenReturn(reputationLevels);

        ReputationLevel value = underTest.getReputationLevelByVillagerId(0);

        assertThat(value).isEqualTo(reputationLevels.get(0));
    }

    @Test
    public void shouldReturnReputationDto() {
        List<ReputationLevel> reputationLevels = List.of(
                new ReputationLevel(0, "name0", 0),
                new ReputationLevel(1, "name1", 10),
                new ReputationLevel(2, "name2", 70),
                new ReputationLevel(3, "name3", 150),
                new ReputationLevel(4, "name4", 250)
        );
        ReputationDto reputationDto = new ReputationDto(0, "name", 0);
        when(authenticationFacade.getPlayerId()).thenReturn(0L);
        when(tradingReputationService.getTradingReputationByPlayerIdAndVillagerId(0, 0))
                .thenReturn(new TradingReputation(0, 0, 0));
        when(reputationLevelDao.getAll()).thenReturn(reputationLevels);
        when(reputationMapper.createFrom(any(), any())).thenReturn(reputationDto);

        ReputationDto value = underTest.getReputationByVillagerId(0);

        assertThat(value).isEqualTo(reputationDto);
    }
}