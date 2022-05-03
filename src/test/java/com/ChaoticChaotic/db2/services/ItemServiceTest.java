package com.ChaoticChaotic.db2.services;

import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ItemMapper;
import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.NotFoundException;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.ChaoticChaotic.db2.services.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    @Spy
    ItemRepository itemRepository;
    @Autowired
    ItemMapper mapper;
    ItemService underTest;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        underTest = new ItemServiceImpl(itemRepository, mapper);
    }

    @Test
    void canSaveItemFromDTO() {
        ItemDTO testItemDTO = ItemDTO.builder()
                .name("testName")
                .quantity(0L)
                .build();

        underTest.saveItemFromDTO(testItemDTO);

        Optional<Item> actual = itemRepository.findByName(testItemDTO.getName());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(testItemDTO.getName());
        assertThat(actual.get().getQuantity()).isEqualTo(testItemDTO.getQuantity());
    }

    @Test
    void trySaveItemFromDTOWithNameAlreadyTakenThenException() {
        Item testItem = Item.builder()
                .name("testName")
                .quantity(0L)
                .build();
        itemRepository.save(testItem);

        ItemDTO testItemDTO = ItemDTO.builder()
                .name("testName")
                .quantity(0L)
                .build();

        assertThatThrownBy(() -> underTest.saveItemFromDTO(testItemDTO))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(("Item with name "
                        + testItemDTO.getName()
                        + " already exists"));
    }

    @Test
    void canDeleteItem() {
        Item testItem = Item.builder()
                .name("testName")
                .quantity(0L)
                .build();
        itemRepository.save(testItem);

        underTest.deleteItem(testItem.getId());

        Optional<Item> actual = itemRepository.findById(testItem.getId());

        assertThat(actual).isEmpty();
    }

    @Test
    void tryDeleteItemWithIdNonexistentThenException() {
        Long nonexistentItemId = 1L;

        assertThatThrownBy(() -> underTest.deleteItem(nonexistentItemId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(("Item with id " + nonexistentItemId + " does not exists"));
    }

    @Test
    void canShowItems() {
        Item testItem = Item.builder()
                .name("testName")
                .quantity(0L)
                .build();
        Item testItem1 = Item.builder()
                .name("testName1")
                .quantity(0L)
                .build();
        itemRepository.save(testItem);
        itemRepository.save(testItem1);

        List<Item> expected = List.of(testItem, testItem1);

        List<Item> actual = underTest.showItems();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void canShowItemsWhenNothingPresent() {
        List<Item> actual = underTest.showItems();

        assertThat(actual).isEmpty();
    }

    @Test
    void canEditItemById() {
        Item testItem = Item.builder()
                .name("testName")
                .quantity(0L)
                .build();
        itemRepository.save(testItem);

        ItemDTO testItemDTO = ItemDTO.builder()
                .name("testNameChanged")
                .quantity(1L)
                .build();

        ItemDTO actual = underTest.editItemById(testItem.getId(), testItemDTO);

        assertThat(actual.getName()).isEqualTo(testItemDTO.getName());
        assertThat(actual.getQuantity()).isEqualTo(testItemDTO.getQuantity());
    }

    @Test
    void tryEditItemWithIdNonexistentIdThenException() {
        Long nonexistentItemId = 1L;

        ItemDTO testItemDTO = ItemDTO.builder()
                .name("testNameChanged")
                .quantity(1L)
                .build();

        assertThatThrownBy(() -> underTest.editItemById(nonexistentItemId, testItemDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(("Item with id " + nonexistentItemId + " does not exists"));
    }

    @Test
    void canFindItemById() {
        Item testItem = Item.builder()
                .name("testName")
                .quantity(0L)
                .build();
        itemRepository.save(testItem);

        Item actual = underTest.findItemById(testItem.getId());

        assertThat(actual).isEqualTo(testItem);
    }

    @Test
    void tryFindItemWithIdNonexistentIdThenException() {
        Long nonexistentItemId = 1L;

        assertThatThrownBy(() -> underTest.findItemById(nonexistentItemId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(("Item with id " + nonexistentItemId + " does not exists"));
    }
}