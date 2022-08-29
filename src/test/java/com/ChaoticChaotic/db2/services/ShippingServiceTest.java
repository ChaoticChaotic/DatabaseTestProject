package com.ChaoticChaotic.db2.services;

import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.DTO.ShippingCreationRequest;
import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ShippingMapper;
import com.ChaoticChaotic.db2.Db2ApplicationTests;
import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.NotFoundException;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.ChaoticChaotic.db2.repository.ShippingRepository;
import com.ChaoticChaotic.db2.repository.TownRepository;
import com.ChaoticChaotic.db2.services.impl.ShippingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShippingServiceTest extends Db2ApplicationTests {

    @Autowired
    ShippingRepository shippingRepository;
    @Autowired
    ShippingMapper mapper;
    @Autowired
    TownRepository townRepository;
    @Autowired
    ItemRepository itemRepository;
    ShippingService underTest;

    @BeforeEach
    void setUp() {
        shippingRepository.deleteAll();
        townRepository.deleteAll();
        itemRepository.deleteAll();
        underTest = new ShippingServiceImpl(shippingRepository, mapper);
    }

    @Test
    void canSaveShippingFromRequest() {
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

        Town testFrom = Town.builder()
                .name("testFrom")
                .distance(0L)
                .build();
        Town testTo = Town.builder()
                .name("testTo")
                .distance(0L)
                .build();
        townRepository.save(testFrom);
        townRepository.save(testTo);

        ShippingCreationRequest testRequest = ShippingCreationRequest.builder()
                .fromTown(testFrom.getName())
                .toTown(testTo.getName())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(2))
                .itemIds(List.of(testItem.getId(), testItem1.getId()))
                .build();

        ShippingDTO actual = underTest.saveFromRequest(testRequest);

        assertThat(actual.getFromTown().getName()).isEqualTo(testRequest.getFromTown());
        assertThat(actual.getToTown().getName()).isEqualTo(testRequest.getToTown());
        assertThat(actual.getStartDate()).isEqualTo(testRequest.getStartDate());
        assertThat(actual.getEndDate()).isEqualTo(testRequest.getEndDate());
        assertThat(actual.getItems().stream().map(ItemDTO::getId).collect(Collectors.toList()))
                .isEqualTo(testRequest.getItemIds());
    }

    @Test
    void trySaveShippingFromRequestWithWrongDatesThenException() {
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

        Town testFrom = Town.builder()
                .name("testFrom")
                .distance(0L)
                .build();
        Town testTo = Town.builder()
                .name("testTo")
                .distance(0L)
                .build();
        townRepository.save(testFrom);
        townRepository.save(testTo);

        ShippingCreationRequest testRequest = ShippingCreationRequest.builder()
                .fromTown(testFrom.getName())
                .toTown(testTo.getName())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().minusMonths(5))
                .itemIds(List.of(testItem.getId(), testItem1.getId()))
                .build();

        assertThatThrownBy(() -> underTest.saveFromRequest(testRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(("Incorrect shipping dates!"));
    }

    @Test
    void canDeleteShippingById() {
        Town testFrom = Town.builder()
                .name("testFrom")
                .distance(0L)
                .build();
        Town testTo = Town.builder()
                .name("testTo")
                .distance(0L)
                .build();
        townRepository.save(testFrom);
        townRepository.save(testTo);

        Shipping testShipping = Shipping.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(2))
                .fromTown(testFrom)
                .toTown(testTo)
                .items(List.of())
                .build();
        shippingRepository.save(testShipping);

        underTest.deleteById(testShipping.getId());
        Optional<Shipping> actual = shippingRepository.findById(testShipping.getId());

        assertThat(actual).isEmpty();
    }

    @Test
    void tryDeleteShippingWithNonexistentIdThenException() {
        Long nonexistentId = 1L;

        assertThatThrownBy(() -> underTest.deleteById(nonexistentId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(("Shipping with id " + nonexistentId + " does not exists"));
    }

    @Test
    void canShowAllShipping() {
        Town testFrom = Town.builder()
                .name("testFrom")
                .distance(0L)
                .build();
        Town testTo = Town.builder()
                .name("testTo")
                .distance(0L)
                .build();
        townRepository.save(testFrom);
        townRepository.save(testTo);

        Shipping testShipping = Shipping.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(2))
                .fromTown(testFrom)
                .toTown(testTo)
                .items(List.of())
                .build();
        Shipping testShipping1 = Shipping.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(2))
                .fromTown(testFrom)
                .toTown(testTo)
                .items(List.of())
                .build();
        shippingRepository.save(testShipping);
        shippingRepository.save(testShipping1);

        List<ShippingDTO> expected = Stream.of(testShipping, testShipping1)
                .map(mapper::returnDTO).collect(Collectors.toList());;

        List<ShippingDTO> actual = underTest.showAll();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void canShowAllShippingWhenNothingPresented() {
        List<ShippingDTO> actual = underTest.showAll();

        assertThat(actual).isEmpty();
    }

    @Test
    void canEditShippingById() {
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

        Town testFrom = Town.builder()
                .name("testFrom")
                .distance(0L)
                .build();
        Town testTo = Town.builder()
                .name("testTo")
                .distance(0L)
                .build();
        townRepository.save(testFrom);
        townRepository.save(testTo);

        Town testFromChanged = Town.builder()
                .name("testFromChanged")
                .distance(0L)
                .build();
        Town testToChanged = Town.builder()
                .name("testToChanged")
                .distance(0L)
                .build();
        townRepository.save(testFromChanged);
        townRepository.save(testToChanged);

        Shipping testShipping = Shipping.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(2))
                .fromTown(testFrom)
                .toTown(testTo)
                .items(List.of())
                .build();
        shippingRepository.save(testShipping);

        ShippingCreationRequest testRequest = ShippingCreationRequest.builder()
                .fromTown(testFromChanged.getName())
                .toTown(testToChanged.getName())
                .startDate(LocalDate.now().plusMonths(1))
                .endDate(LocalDate.now().plusMonths(2))
                .itemIds(List.of(testItem.getId(), testItem1.getId()))
                .build();

        ShippingDTO actual = underTest.editById(testShipping.getId(), testRequest);

        assertThat(actual.getFromTown().getName()).isEqualTo(testRequest.getFromTown());
        assertThat(actual.getToTown().getName()).isEqualTo(testRequest.getToTown());
        assertThat(actual.getStartDate()).isEqualTo(testRequest.getStartDate());
        assertThat(actual.getEndDate()).isEqualTo(testRequest.getEndDate());
        assertThat(actual.getItems().stream().map(ItemDTO::getId).collect(Collectors.toList()))
                .isEqualTo(testRequest.getItemIds());
    }

    @Test
    void tryEditShippingWithNonexistentIdThenException() {
        Long nonexistentId = 1L;

        Town testFromChanged = Town.builder()
                .name("testFromChanged")
                .distance(0L)
                .build();
        Town testToChanged = Town.builder()
                .name("testToChanged")
                .distance(0L)
                .build();
        townRepository.save(testFromChanged);
        townRepository.save(testToChanged);

        ShippingCreationRequest testRequest = ShippingCreationRequest.builder()
                .fromTown(testFromChanged.getName())
                .toTown(testToChanged.getName())
                .startDate(LocalDate.now().plusMonths(1))
                .endDate(LocalDate.now().plusMonths(2))
                .itemIds(List.of())
                .build();

        assertThatThrownBy(() -> underTest.editById(nonexistentId, testRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(("Shipping with id " + nonexistentId + " does not exists"));
    }
}