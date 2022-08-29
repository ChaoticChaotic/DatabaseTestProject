package com.ChaoticChaotic.db2.repository;

import com.ChaoticChaotic.db2.entity.Town;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TownRepositoryTest {

    @Autowired
    TownRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void findByName() {
        Town testTown = Town.builder()
                .name("testName")
                .distance(0L)
                .build();
        underTest.save(testTown);

        Optional<Town> actual = underTest.findByName(testTown.getName());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(testTown);
    }
}