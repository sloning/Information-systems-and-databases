package com.isbd.dao;

import com.isbd.model.InventoryItem;
import com.isbd.model.Withdrawal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WithdrawalDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private WithdrawalCompositionDao withdrawalCompositionDao;
    private WithdrawalDao underTest;

    @BeforeEach
    void setUp() {
        underTest = new WithdrawalDao(jdbcTemplate, withdrawalCompositionDao);
    }

    @Test
    void shouldReturnById() {
        Withdrawal withdrawal = new Withdrawal(1,
                1,
                1,
                List.of(new InventoryItem(1, "name", "iconAddress", 1)));
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<ResultSetExtractor<Optional<Withdrawal>>>any(), any()))
                .thenReturn(Optional.of(withdrawal));

        Optional<Withdrawal> value = underTest.get(withdrawal.getId());

        assertThat(value).isPresent();
        assertThat(value.get()).isEqualTo(withdrawal);
    }

    @Test
    void shouldReturnByPlayer() {
        Withdrawal withdrawal = new Withdrawal(1,
                1,
                1,
                List.of(new InventoryItem(1, "name", "iconAddress", 1)));
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Withdrawal>>any(), any()))
                .thenReturn(List.of(withdrawal));

        List<Withdrawal> value = underTest.getByPlayer(withdrawal.getPlayerId());

        assertThat(value).isNotEmpty();
        assertThat(value.get(0)).isEqualTo(withdrawal);
    }

    @Test
    void save() {
        Withdrawal withdrawal = new Withdrawal(1,
                1,
                1,
                List.of(new InventoryItem(1, "name", "iconAddress", 1)));
        when(jdbcTemplate.update(anyString(), anyInt(), anyLong())).thenReturn(1);

        int rowsUpdated = underTest.save(withdrawal);

        assertThat(rowsUpdated).isGreaterThan(0);
    }
}