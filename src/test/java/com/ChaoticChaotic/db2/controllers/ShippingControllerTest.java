package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.DTO.ShippingCreationRequest;
import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ShippingMapper;
import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.ChaoticChaotic.db2.repository.ShippingRepository;
import com.ChaoticChaotic.db2.repository.TownRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class ShippingControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ShippingRepository shippingRepository;
    @Autowired
    ShippingMapper mapper;
    @Autowired
    TownRepository townRepository;
    @Autowired
    ItemRepository itemRepository;


    @BeforeEach
    void setUp() {
        shippingRepository.deleteAll();
    }

    @Test
    void getAllShipping() throws Exception {
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
                .endDate(LocalDate.now())
                .fromTown(testFrom)
                .toTown(testTo)
                .items(List.of())
                .build();

        Shipping testShipping1 = Shipping.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .fromTown(testFrom)
                .toTown(testTo)
                .items(List.of())
                .build();
        shippingRepository.save(testShipping);
        shippingRepository.save(testShipping1);

        List<ShippingDTO> expected = List.of(mapper.returnDTO(testShipping), mapper.returnDTO(testShipping1));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/shipping/all")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<ShippingDTO> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertThat(actual).containsAll(expected);
    }

    @Test
    void deleteShipping() throws Exception {
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


        mockMvc
                .perform(delete("/api/shipping/delete")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header("shippingId", testShipping.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<Shipping> actual = shippingRepository.findById(testShipping.getId());
        assertThat(actual).isEmpty();
    }

    @Test
    void canSaveShipping() throws Exception {
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

        ShippingCreationRequest testRequest = ShippingCreationRequest.builder()
                .fromTown(testFrom.getName())
                .toTown(testTo.getName())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(2))
                .itemIds(List.of(testItem.getId(), testItem1.getId()))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(testRequest);

        MvcResult mvcResult = mockMvc
                .perform(post("/api/shipping/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ShippingDTO actual = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertThat(actual.getFromTown().getName()).isEqualTo(testRequest.getFromTown());
        assertThat(actual.getToTown().getName()).isEqualTo(testRequest.getToTown());
        assertThat(actual.getStartDate()).isEqualTo(testRequest.getStartDate());
        assertThat(actual.getEndDate()).isEqualTo(testRequest.getEndDate());
        assertThat(actual.getItems().stream().map(ItemDTO::getId).collect(Collectors.toList()))
                .isEqualTo(testRequest.getItemIds());
    }

    @Test
    void canEditShipping() throws Exception {
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
                .name("testFromChange")
                .distance(0L)
                .build();
        Town testToChanged = Town.builder()
                .name("testToChange")
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

        LocalDate changedDate = LocalDate.now().plusMonths(1);

        ShippingCreationRequest testRequest = ShippingCreationRequest.builder()
                .fromTown(testFrom.getName() + "Change")
                .toTown(testTo.getName() + "Change")
                .startDate(changedDate)
                .endDate(changedDate.plusMonths(1))
                .itemIds(List.of())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(testRequest);

        MvcResult mvcResult = mockMvc
                .perform(patch("/api/shipping/edit")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(requestJson)
                        .header("shippingId", testShipping.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ShippingDTO actual = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertThat(actual.getFromTown().getName()).isEqualTo(testFromChanged.getName());
        assertThat(actual.getToTown().getName()).isEqualTo(testToChanged.getName());
        assertThat(actual.getStartDate()).isEqualTo(changedDate);
        assertThat(actual.getEndDate()).isEqualTo(changedDate.plusMonths(1));
        assertThat(actual.getItems().stream().map(ItemDTO::getId).collect(Collectors.toList()))
                .isEqualTo(testRequest.getItemIds());
    }
}