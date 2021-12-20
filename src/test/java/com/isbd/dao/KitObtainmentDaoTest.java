package com.isbd.dao;

import com.isbd.model.ObtainedKit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KitObtainmentDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private KitDao kitDao;
    private KitObtainmentDao underTest;

    @BeforeEach
    void setUp() {
        underTest = new KitObtainmentDao(jdbcTemplate, kitDao);
    }

    @Test
    void shouldReturnByPlayerAndKitWhenGotListWithMultipleValues() {
        List<ObtainedKit> obtainedKits = List.of(
                new ObtainedKit(1, "name", 1, new ArrayList<>(), LocalDateTime.now()),
                new ObtainedKit(2, "name2", 1, new ArrayList<>(), LocalDateTime.now())
        );
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<ResultSetExtractor<List<ObtainedKit>>>any(),
                anyLong(), anyInt())).thenReturn(obtainedKits);

        Optional<ObtainedKit> value = underTest.getByPlayerAndKit(1, obtainedKits.get(0).getId());

        assertThat(value).isNotEmpty();
        assertThat(value.get()).isEqualTo(obtainedKits.get(0));
    }

    @Test
    void shouldReturnByPlayerAndKitWhenGotEmptyList() {
        List<ObtainedKit> obtainedKits = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<ResultSetExtractor<List<ObtainedKit>>>any(),
                anyLong(), anyInt())).thenReturn(obtainedKits);

        Optional<ObtainedKit> value = underTest.getByPlayerAndKit(1, 1);

        assertThat(value).isEmpty();
    }
}