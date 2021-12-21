package com.isbd.service;

import com.isbd.dao.WithdrawalDao;
import com.isbd.dto.WithdrawalDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.InventoryItem;
import com.isbd.model.Withdrawal;
import com.isbd.security.AuthenticationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WithdrawalServiceTest {
    @Mock
    private WithdrawalDao withdrawalDao;
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private InventoryService inventoryService;
    private WithdrawalService underTest;

    @BeforeEach
    void setUp() {
        underTest = new WithdrawalService(withdrawalDao, authenticationFacade, inventoryService);
    }

    @Test
    void shouldReturnByPlayer() {
        List<Withdrawal> withdrawals = List.of(
                new Withdrawal(1, 1, 1,
                        List.of(new InventoryItem(1, "name", "iconAddress", 1))),
                new Withdrawal(2, 1, 1,
                        List.of(new InventoryItem(1, "name", "iconAddress", 1)))
        );
        when(authenticationFacade.getPlayerId()).thenReturn(1L);
        when(withdrawalDao.getByPlayer(1L)).thenReturn(withdrawals);

        List<Withdrawal> value = underTest.getByPlayer();

        assertThat(value).isNotEmpty();
        assertThat(value).isEqualTo(withdrawals);
    }

    @Test
    void shouldThrowExceptionWhenGotEmptyList() {
        List<Withdrawal> withdrawals = new ArrayList<>();
        when(authenticationFacade.getPlayerId()).thenReturn(1L);
        when(withdrawalDao.getByPlayer(1L)).thenReturn(withdrawals);

        assertThatThrownBy(() -> underTest.getByPlayer()).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldCreateNewWithdrawal() {
        WithdrawalDto withdrawalDto = new WithdrawalDto(1);
        List<InventoryItem> inventoryItems =
                List.of(new InventoryItem(1, "name", "iconAddress", 1));
        Withdrawal withdrawal = new Withdrawal(0, 1L, 1, inventoryItems);
        when(authenticationFacade.getPlayerId()).thenReturn(1L);
        when(inventoryService.getByPlayerId(1L)).thenReturn(inventoryItems);
        when(withdrawalDao.save(any())).thenReturn(1);

        underTest.createWithdrawal(withdrawalDto);

        verify(withdrawalDao).save(withdrawal);
    }

    @Test
    void shouldThrowExceptionWhenNewWithdrawalNotSaved() {
        WithdrawalDto withdrawalDto = new WithdrawalDto(1);
        List<InventoryItem> inventoryItems =
                List.of(new InventoryItem(1, "name", "iconAddress", 1));
        when(authenticationFacade.getPlayerId()).thenReturn(1L);
        when(inventoryService.getByPlayerId(1L)).thenReturn(inventoryItems);
        when(withdrawalDao.save(any())).thenReturn(0);

        assertThatThrownBy(() -> underTest.createWithdrawal(withdrawalDto)).isInstanceOf(EntityNotSavedException.class);
    }
}