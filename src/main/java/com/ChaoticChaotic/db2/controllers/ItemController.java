package com.ChaoticChaotic.db2.controllers;


import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ItemMapper;
import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;
    private ItemMapper mapper;

    @GetMapping("/items")
    private ResponseEntity<List<ItemDTO>> showItems(){
      return ResponseEntity.ok().body(itemService.showItems()
              .stream()
              .map(mapper::returnDTO)
              .collect(Collectors.toList()));
    }

    @DeleteMapping("/items/{id}")
    private ResponseEntity<Object> deleteItem(@PathVariable("id") Long id){
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/items")
    private ResponseEntity<ItemDTO> saveItem(@RequestBody ItemDTO itemDTO){
        return ResponseEntity.status(201).body(itemService.saveItemFromDTO(itemDTO));
    }

}
