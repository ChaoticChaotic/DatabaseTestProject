package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ItemMapper;
import com.ChaoticChaotic.db2.Db2ApplicationTests;
import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class ItemControllerTest extends Db2ApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemMapper mapper;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void canGetAllItems() throws Exception {
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

        List<ItemDTO> expected = List.of(mapper.returnDTO(testItem), mapper.returnDTO(testItem1));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/item/all")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<ItemDTO> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertThat(actual).containsAll(expected);
    }

    @Test
    void canSaveItem() throws Exception {
        ItemDTO testItemDTO = ItemDTO.builder()
                .name("testName")
                .quantity(0L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(testItemDTO);

        mockMvc
                .perform(post("/api/item/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Optional<Item> actual = itemRepository.findByName(testItemDTO.getName());
        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(testItemDTO.getName());
        assertThat(actual.get().getQuantity()).isEqualTo(testItemDTO.getQuantity());
    }

    @Test
    void canDeleteItem() throws Exception {
        Item testItem = Item.builder()
                .name("testName")
                .quantity(0L)
                .build();
        itemRepository.save(testItem);

        mockMvc
                .perform(delete("/api/item/delete/" + testItem.getId())
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<Item> actual = itemRepository.findById(testItem.getId());
        assertThat(actual).isEmpty();
    }


    @Test
    void canEditTown() throws Exception {
        Item testItem = Item.builder()
                .name("testName")
                .quantity(0L)
                .build();
        itemRepository.save(testItem);

        ItemDTO testItemDTO = ItemDTO.builder()
                .name("testChangedName")
                .quantity(10L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(testItemDTO);


        mockMvc
                .perform(patch("/api/item/edit/" + testItem.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<Item> actual = itemRepository.findById(testItem.getId());
        assertThat(actual.get().getName()).isEqualTo(testItemDTO.getName());
        assertThat(actual.get().getQuantity()).isEqualTo(testItemDTO.getQuantity());
    }
}