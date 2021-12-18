package com.isbd.dao;

import com.isbd.model.Biome;
import com.isbd.model.Pageable;
import com.isbd.model.Village;
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
class VillageDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private BiomeDao biomeDao;
    private VillageDao underTest;

    @BeforeEach
    void setUp() {
        underTest = new VillageDao(biomeDao, jdbcTemplate);
    }

    @Test
    void shouldReturnById() {
        Village village = new Village(1, "name", 0, 0, new Biome(1, "name"));
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<ResultSetExtractor<Optional<Village>>>any(), any()))
                .thenReturn(Optional.of(village));

        Optional<Village> value = underTest.get(village.getId());

        assertThat(value).isPresent();
        assertThat(value.get()).isEqualTo(village);
    }

    @Test
    void shouldReturnAll() {
        Village village = new Village(1, "name", 0, 0, new Biome(1, "name"));
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Village>>any(), any(), any()))
                .thenReturn(List.of(village));

        List<Village> value = underTest.getAll(Pageable.all());

        assertThat(value).isNotEmpty();
        assertThat(value.get(0)).isEqualTo(village);
    }

    @Test
    void shouldSave() {
        Village village = new Village(1, "name", 0, 0, new Biome(1, "name"));
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(1);

        int value = underTest.save(village);

        assertThat(value).isGreaterThan(0);
    }

    @Test
    void shouldUpdate() {
        Village village = new Village(1, "name", 0, 0, new Biome(1, "name"));
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), anyInt(), anyInt()))
                .thenReturn(1);

        int value = underTest.update(village);

        assertThat(value).isGreaterThan(0);
    }

    @Test
    void shouldDelete() {
        Village village = new Village(1, "name", 0, 0, new Biome(1, "name"));
        when(jdbcTemplate.update(anyString(), anyInt()))
                .thenReturn(1);

        int value = underTest.delete(village);

        assertThat(value).isGreaterThan(0);
    }
}