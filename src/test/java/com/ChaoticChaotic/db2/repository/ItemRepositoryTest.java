package com.ChaoticChaotic.db2.repository;

import com.ChaoticChaotic.db2.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void findByName() {
        Item testItem = Item.builder()
                .name("testName")
                .quantity(0L)
                .build();
        underTest.save(testItem);

        Optional<Item> actual = underTest.findByName(testItem.getName());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(testItem);
    }
}