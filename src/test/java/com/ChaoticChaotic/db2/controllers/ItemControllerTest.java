package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.ChaoticChaotic.db2.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository repository;


    @BeforeEach
    void setUp(){
        Item item1 = new Item("item name", 15L);
        Item item2 = new Item("item name2", 12L);
        repository.save(item1);
        repository.save(item2);
    }

    @Test
    void canGetAllItems() throws Exception {
        mockMvc.perform(get("/items")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    void canDeleteItem() throws Exception {
        mockMvc.perform(delete("/items/1")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Disabled
    void canAddItem() throws Exception {
        Item item3 = new Item("item name3", 12L);
        repository.save(item3);
        mockMvc.perform(post("/items"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getHandler().equals(item3);
    }
}