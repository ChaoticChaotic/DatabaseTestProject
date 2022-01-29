package com.ChaoticChaotic.db2.controllers;


import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Long addItem(@RequestBody Item item){
        itemService.addItem(item);
        return item.getItemId();
    }

}
