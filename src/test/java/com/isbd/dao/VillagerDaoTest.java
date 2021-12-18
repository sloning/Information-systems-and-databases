package com.isbd.dao;

import com.isbd.model.Pageable;
import com.isbd.model.Profession;
import com.isbd.model.Villager;
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
class VillagerDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private ProfessionDao professionDao;
    private VillagerDao underTest;

    @BeforeEach
    void setUp() {
        underTest = new VillagerDao(jdbcTemplate, professionDao);
    }

    @Test
    void shouldReturnById() {
        Villager villager = new Villager(1, "name", new Profession(1, "name"), 1);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<ResultSetExtractor<Optional<Villager>>>any(), any()))
                .thenReturn(Optional.of(villager));

        Optional<Villager> value = underTest.get(1);

        assertThat(value).isPresent();
        assertThat(value.get()).isEqualTo(villager);
    }

    @Test
    void shouldReturnAll() {
        Villager villager = new Villager(1, "name", new Profession(1, "name"), 1);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Villager>>any(), any()))
                .thenReturn(List.of(villager));

        List<Villager> value = underTest.getAll(Pageable.all());

        assertThat(value).isNotEmpty();
        assertThat(value.get(0)).isEqualTo(villager);
    }

    @Test
    void shouldSave() {
        Villager villager = new Villager(1, "name", new Profession(1, "name"), 1);
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), anyInt())).thenReturn(1);

        int value = underTest.save(villager);

        assertThat(value).isGreaterThan(0);
    }

    @Test
    void shouldUpdate() {
        Villager villager = new Villager(1, "name", new Profession(1, "name"), 1);
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), anyInt(), anyInt())).thenReturn(1);

        int value = underTest.update(villager);

        assertThat(value).isGreaterThan(0);
    }

    @Test
    void shouldDelete() {
        Villager villager = new Villager(1, "name", new Profession(1, "name"), 1);
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);

        int value = underTest.delete(villager);

        assertThat(value).isGreaterThan(0);
    }

    @Test
    void shouldReturnVillagersByVillage() {
        Villager villager = new Villager(1, "name", new Profession(1, "name"), 1);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Villager>>any(), any()))
                .thenReturn(List.of(villager));

        List<Villager> value = underTest.getVillagersByVillage(villager.getVillageId(), Pageable.all());

        assertThat(value).isNotEmpty();
        assertThat(value.get(0)).isEqualTo(villager);
    }
}