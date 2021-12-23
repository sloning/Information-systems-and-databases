package com.isbd.service;

import com.isbd.dao.VillagerDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Pageable;
import com.isbd.model.Profession;
import com.isbd.model.Villager;
import com.isbd.service.mapper.VillagerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VillagerServiceTest {
    @Mock
    private VillagerDao villagerDao;
    @Mock
    private VillagerMapper villagerMapper;
    private VillagerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new VillagerService(villagerDao, villagerMapper);
    }

    @Test
    public void shouldReturnVillager() {
        Villager villager = new Villager(0, "name", new Profession(0, "name"), 0);
        when(villagerDao.get(villager.getId())).thenReturn(Optional.of(villager));

        Villager value = underTest.getVillager(villager.getId());

        assertThat(value).isEqualTo(villager);
    }

    @Test
    public void shouldThrowExceptionWhenGotEmptyOptional() {
        when(villagerDao.get(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getVillager(0)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void shouldReturnListOfVillagers() {
        List<Villager> villagers = List.of(
                new Villager(0, "name0", new Profession(0, "name"), 0),
                new Villager(1, "name1", new Profession(0, "name"), 0)
        );
        when(villagerDao.getAll(any())).thenReturn(villagers);

        List<Villager> value = underTest.getVillagers(Pageable.all());

        assertThat(value).isNotEmpty();
        assertThat(value).isEqualTo(villagers);
    }

    @Test
    public void shouldThrowExceptionWhenGotEmptyList() {
        List<Villager> villagers = new ArrayList<>();
        when(villagerDao.getAll(any())).thenReturn(villagers);

        assertThatThrownBy(() -> underTest.getVillagers(Pageable.all())).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void shouldReturnListOfVillagersOfVillage() {
        List<Villager> villagers = List.of(
                new Villager(0, "name0", new Profession(0, "name"), 0),
                new Villager(1, "name1", new Profession(0, "name"), 0)
        );
        when(villagerDao.getVillagersByVillage(anyInt(), any())).thenReturn(villagers);

        List<Villager> value = underTest.getVillagersOfVillage(0, Pageable.all());

        assertThat(value).isNotEmpty();
        assertThat(value).isEqualTo(villagers);
    }

    @Test
    public void shouldThrowExceptionWhenGotEmptyListOnVillagersOfVillageCall() {
        List<Villager> villagers = new ArrayList<>();
        when(villagerDao.getVillagersByVillage(anyInt(), any())).thenReturn(villagers);

        assertThatThrownBy(() -> underTest.getVillagersOfVillage(0, Pageable.all())).isInstanceOf(EntityNotFoundException.class);
    }
}