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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private ResultSetExtractor<List<InventoryItem>> resultSetExtractor;
    private InventoryDao underTest;

    @BeforeEach
    void setUp() {
        underTest = new InventoryDao(jdbcTemplate, resultSetExtractor);
    }

    @Test
    void shouldReturnPlayersEmeraldsWhenGotListWithMultipleValues() {
        List<InventoryItem> inventoryItems = List.of(
                new InventoryItem(1, "name1", "iconAddress", 322),
                new InventoryItem(2, "name2", "iconAddress", 0)
        );
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<ResultSetExtractor<List<InventoryItem>>>any(), any()))
                .thenReturn(inventoryItems);

        Optional<InventoryItem> value = underTest.getPlayersEmeralds(1);

        assertThat(value).isPresent();
        assertThat(value.get()).isEqualTo(inventoryItems.get(0));
    }

    @Test
    void shouldReturnPlayersEmeraldsWhenGotEmptyList() {
        List<InventoryItem> inventoryItems = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<ResultSetExtractor<List<InventoryItem>>>any(), any()))
                .thenReturn(inventoryItems);

        Optional<InventoryItem> value = underTest.getPlayersEmeralds(1);

        assertThat(value).isEmpty();
    }
}