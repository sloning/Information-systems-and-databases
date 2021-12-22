package com.isbd.service;

import com.isbd.dao.VillageDao;
import com.isbd.dto.VillageDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Biome;
import com.isbd.model.Pageable;
import com.isbd.model.Village;
import com.isbd.service.mapper.VillageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VillageServiceTest {
    @Mock
    private VillageDao villageDao;
    @Mock
    private VillageMapper villageMapper;
    private VillageService underTest;

    private static Stream<Arguments> provideCoordinates() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(6, 6),
                Arguments.of(6, -6),
                Arguments.of(-6, -6),
                Arguments.of(-6, 6)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new VillageService(villageDao, villageMapper);
    }

    @Test
    public void shouldReturnVillage() {
        Village village = new Village(1, "name", 0, 0, new Biome(1, "name"));
        when(villageDao.get(village.getId())).thenReturn(Optional.of(village));

        Village value = underTest.getVillage(village.getId());

        assertThat(value).isEqualTo(village);
    }

    @Test
    public void shouldThrowExceptionWhenGotEmptyOptional() {
        when(villageDao.get(0)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getVillage(0)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void shouldReturnListOfVillages() {
        List<Village> villages = List.of(
                new Village(0, "name0", 0, 0, new Biome(0, "name")),
                new Village(1, "name1", 1, 1, new Biome(1, "name"))
        );
        when(villageDao.getAll(any())).thenReturn(villages);

        List<Village> value = underTest.getVillages(Pageable.all());

        assertThat(value).isNotEmpty();
        assertThat(value).isEqualTo(villages);
    }

    @Test
    public void shouldThrowExceptionWhenGotEmptyList() {
        List<Village> villages = new ArrayList<>();
        when(villageDao.getAll(any())).thenReturn(villages);

        assertThatThrownBy(() -> underTest.getVillages(Pageable.all())).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void shouldReturnVillageDto() {
        Village village = new Village(1, "name", 0, 0, new Biome(1, "name"));
        VillageDto villageDto = new VillageDto(0, "name0", 0, 0, new Biome(0, "name"), true, 1);
        when(villageMapper.createFrom(any())).thenReturn(villageDto);
        when(villageDao.get(village.getId())).thenReturn(Optional.of(village));

        VillageDto value = underTest.getVillageWithExtraData(village.getId());

        assertThat(value).isEqualTo(villageDto);
    }

    @ParameterizedTest
    @MethodSource("provideCoordinates")
    public void shouldReturnNearestVillage(int xCoordinate, int zCoordinate) {
        List<Village> villages = List.of(
                new Village(0, "center", 0, 0, new Biome(0, "name")),
                new Village(1, "firstQuarter", 10, 10, new Biome(0, "name")),
                new Village(2, "secondQuarter", 10, -10, new Biome(0, "name")),
                new Village(3, "thirdQuarter", -10, -10, new Biome(0, "name")),
                new Village(4, "fourthQuarter", -10, 10, new Biome(0, "name"))
        );
        when(villageDao.getAll(any())).thenReturn(villages);

        Village value = underTest.getNearestVillage(xCoordinate, zCoordinate);

        if (xCoordinate == 0 && zCoordinate == 0) {
            assertThat(value).isEqualTo(villages.get(0));
        } else if (xCoordinate > 0 && zCoordinate > 0) {
            assertThat(value).isEqualTo(villages.get(1));
        } else if (xCoordinate > 0 && zCoordinate < 0) {
            assertThat(value).isEqualTo(villages.get(2));
        } else if (xCoordinate < 0 && zCoordinate < 0) {
            assertThat(value).isEqualTo(villages.get(3));
        } else {
            assertThat(value).isEqualTo(villages.get(4));
        }
    }
}