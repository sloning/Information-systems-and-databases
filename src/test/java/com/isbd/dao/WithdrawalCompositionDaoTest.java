package com.isbd.dao;

import com.isbd.model.InventoryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WithdrawalCompositionDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private ResultSetExtractor<List<InventoryItem>> resultSetExtractor;
    private WithdrawalCompositionDao underTest;

    @BeforeEach
    void setUp() {
        underTest = new WithdrawalCompositionDao(jdbcTemplate, resultSetExtractor);
    }

    @Test
    public void shouldReturnByWithdrawalId() {
        List<InventoryItem> inventoryItems = List.of(
                new InventoryItem(1, "name", "iconAddress", 1));
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<ResultSetExtractor<List<InventoryItem>>>any(), any()))
                .thenReturn(inventoryItems);

        List<InventoryItem> value = underTest.getByWithdrawal(1);

        assertThat(value).isEqualTo(inventoryItems);
    }
}