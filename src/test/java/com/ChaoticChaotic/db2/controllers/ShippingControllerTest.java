package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ShippingMapper;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.repository.ShippingRepository;
import com.ChaoticChaotic.db2.repository.TownRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .endDate(LocalDate.now())
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
    @Disabled
    void saveShipping() {
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

    }

    @Test
    @Disabled
    void editShipping() {
    }
}