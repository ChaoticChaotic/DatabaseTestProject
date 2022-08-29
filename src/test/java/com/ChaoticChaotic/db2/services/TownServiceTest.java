package com.ChaoticChaotic.db2.services;

import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.DTO.mappers.TownMapper;
import com.ChaoticChaotic.db2.Db2ApplicationTests;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.NotFoundException;
import com.ChaoticChaotic.db2.repository.TownRepository;
import com.ChaoticChaotic.db2.services.impl.TownServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TownServiceTest extends Db2ApplicationTests {

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

        underTest.saveFromDTO(testTownDTO);

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


        assertThatThrownBy(() -> underTest.saveFromDTO(testTownDTO))
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

        underTest.delete(testTown.getId());

        Optional<Town> actual = townRepository.findByName(testTown.getName());

        assertThat(actual).isEmpty();
    }

    @Test
    void tryDeleteTownWithNonexistentIdThenException() {
        Long nonexistentId = 1L;

        assertThatThrownBy(() -> underTest.delete(nonexistentId))
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

        List<TownDTO> expected = Stream.of(testTown, testTown1)
                .map(mapper::returnDTO).collect(Collectors.toList());

        List<TownDTO> actual = underTest.show();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void canShowTownsWhenNothingPresentThenException() {
        List<TownDTO> actual = underTest.show();

        assertThat(actual).isEmpty();
    }

    @Test
    void canFindTownByName() {
        Town testTown = Town.builder()
                .name("testName")
                .distance(0L)
                .build();
        townRepository.save(testTown);

        Town actual = underTest.findByName(testTown.getName());

        assertThat(actual).isEqualTo(testTown);
    }

    @Test
    void tryFindTownByNameNonexistentIdThenException() {
        String nonexistentName = "Nonexistent";

        assertThatThrownBy(() -> underTest.findByName(nonexistentName))
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

        TownDTO actual = underTest.editById(testTown.getId(), testTownDTO);

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

        assertThatThrownBy(() -> underTest.editById(nonexistentId, testTownDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(("Town with id " + nonexistentId + " does not exists"));
    }
}