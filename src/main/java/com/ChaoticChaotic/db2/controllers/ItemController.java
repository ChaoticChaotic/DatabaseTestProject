package com.ChaoticChaotic.db2.controllers;


import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;


    @GetMapping("/items")
    private List<Item> showItems(){
      return itemService.showItems();
    }

    @DeleteMapping("/items/{id}")
    private void deleteItem(@PathVariable("id") Long id){
        itemService.deleteItem(id);
    }

    @PostMapping("/items")
    private ResponseEntity<ItemDTO> saveItem(@RequestBody ItemDTO itemDTO){
        return ResponseEntity.status(201).body(itemService.saveItemFromDTO(itemDTO));
    }

}
