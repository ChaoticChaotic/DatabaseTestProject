package com.ChaoticChaotic.db2.services;

import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.DTO.mappers.TownMapper;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.NotFoundException;
import com.ChaoticChaotic.db2.repository.TownRepository;
import com.ChaoticChaotic.db2.services.impl.TownServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TownServiceTest {

    @Autowired
    TownRepository townRepository;
    @Autowired
    TownMapper mapper;
    TownService underTest;

    @BeforeEach
    void setUp() {
        townRepository.deleteAll();
        underTest = new TownServiceImpl(townRepository, mapper);
    }

    @Test
    void canSaveTownFromDTO() {
        TownDTO testTownDTO = TownDTO.builder()
                .name("testName")
                .distance(0L)
                .build();

        underTest.saveTownFromDTO(testTownDTO);

        Optional<Town> actual = townRepository.findByName(testTownDTO.getName());

        assertThat(actual.get().getName()).isEqualTo(testTownDTO.getName());
        assertThat(actual.get().getDistance()).isEqualTo(testTownDTO.getDistance());
    }

    @Test
    void trySaveTownFromDTOWithNameAlreadyTakenThenException() {
        TownDTO testTownDTO = TownDTO.builder()
                .name("testName")
                .distance(0L)
                .build();

        Town testTown = Town.builder()
                .name("testName")
                .distance(0L)
                .build();
        townRepository.save(testTown);


        assertThatThrownBy(() -> underTest.saveTownFromDTO(testTownDTO))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(("Town with name "
                        + testTownDTO.getName()
                        + " already exists"));
    }

    @Test
    void canDeleteTown() {
        Town testTown = Town.builder()
                .name("testName")
                .distance(0L)
                .build();
        townRepository.save(testTown);

        underTest.deleteTown(testTown.getId());

        Optional<Town> actual = townRepository.findByName(testTown.getName());

        assertThat(actual).isEmpty();
    }

    @Test
    void tryDeleteTownWithNonexistentIdThenException() {
        Long nonexistentId = 1L;

        assertThatThrownBy(() -> underTest.deleteTown(nonexistentId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(("Town with id " + nonexistentId + " does not exists"));

    }

    @Test
    void canShowTowns() {
        Town testTown = Town.builder()
                .name("testName")
                .distance(0L)
                .build();
        Town testTown1 = Town.builder()
                .name("testName1")
                .distance(0L)
                .build();
        townRepository.save(testTown);
        townRepository.save(testTown1);

        List<Town> expected = List.of(testTown, testTown1);

        List<Town> actual = underTest.showTowns();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void canShowTownsWhenNothingPresentThenException() {
        List<Town> actual = underTest.showTowns();

        assertThat(actual).isEmpty();
    }

    @Test
    void canFindTownByName() {
        Town testTown = Town.builder()
                .name("testName")
                .distance(0L)
                .build();
        townRepository.save(testTown);

        Town actual = underTest.findTownByName(testTown.getName());

        assertThat(actual).isEqualTo(testTown);
    }

    @Test
    void tryFindTownByNameNonexistentIdThenException() {
        String nonexistentName = "Nonexistent";

        assertThatThrownBy(() -> underTest.findTownByName(nonexistentName))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(("Town with name " + nonexistentName + " not found"));
    }

    @Test
    void canEditTownById() {
        Town testTown = Town.builder()
                .name("testName")
                .distance(0L)
                .build();
        townRepository.save(testTown);

        TownDTO testTownDTO = TownDTO.builder()
                .name("testNameChanged")
                .distance(1L)
                .build();

        TownDTO actual = underTest.editTownById(testTown.getId(), testTownDTO);

        assertThat(actual.getName()).isEqualTo(testTownDTO.getName());
        assertThat(actual.getDistance()).isEqualTo(testTownDTO.getDistance());
    }

    @Test
    void tryEditTownWithNonexistentIdThenException() {
        Long nonexistentId = 1L;

        TownDTO testTownDTO = TownDTO.builder()
                .name("testNameChanged")
                .distance(1L)
                .build();

        assertThatThrownBy(() -> underTest.editTownById(nonexistentId, testTownDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(("Town with id " + nonexistentId + " does not exists"));
    }
}