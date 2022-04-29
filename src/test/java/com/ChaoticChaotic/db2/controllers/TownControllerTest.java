package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.DTO.mappers.TownMapper;
import com.ChaoticChaotic.db2.entity.Town;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class TownControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TownRepository townRepository;
    @Autowired
    TownMapper mapper;

    @BeforeEach
    void setUp() {
        townRepository.deleteAll();
    }

    @Test
    void canGetAllTowns() throws Exception {
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

        List<TownDTO> expected = List.of(mapper.returnDTO(testTown), mapper.returnDTO(testTown1));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/town/all")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<TownDTO> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertThat(actual).containsAll(expected);
    }

    @Test
    void canDeleteTown() throws Exception {
        Town testTown = Town.builder()
                .name("testName")
                .distance(0L)
                .build();
        townRepository.save(testTown);

        mockMvc
                .perform(delete("/api/town/delete")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header("townId", testTown.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<Town> actual = townRepository.findById(testTown.getId());
        assertThat(actual).isEmpty();
    }

    @Test
    void canAddTown() throws Exception {
        TownDTO testTownDTO = TownDTO.builder()
                .name("testName")
                .distance(0L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(testTownDTO);

        mockMvc
                .perform(post("/api/town/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Optional<Town> actual = townRepository.findByName(testTownDTO.getName());
        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(testTownDTO.getName());
        assertThat(actual.get().getDistance()).isEqualTo(testTownDTO.getDistance());
    }

    @Test
    void canEditTown() throws Exception {
        Town testTown = Town.builder()
                .name("testName")
                .distance(0L)
                .build();
        townRepository.save(testTown);

        TownDTO testTownDTO = TownDTO.builder()
                .name("testNameChanged")
                .distance(0L)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(testTownDTO);


        mockMvc
                .perform(patch("/api/town/edit")
                        .header("townId", testTown.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<Town> actual = townRepository.findById(testTown.getId());
        assertThat(actual.get().getName()).isEqualTo(testTownDTO.getName());
        assertThat(actual.get().getDistance()).isEqualTo(testTownDTO.getDistance());
    }
}