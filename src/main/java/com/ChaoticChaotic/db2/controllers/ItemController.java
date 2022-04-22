package com.ChaoticChaotic.db2.controllers;


import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ItemMapper;
import com.ChaoticChaotic.db2.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;
    private ItemMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<ItemDTO>> showItems(){
      return ResponseEntity.ok().body(itemService.showItems()
              .stream()
              .map(mapper::returnDTO)
              .collect(Collectors.toList()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteItem(@RequestHeader(value = "itemId") Long id){
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    public ResponseEntity<ItemDTO> saveItem(@RequestBody ItemDTO itemDTO){
        return ResponseEntity.status(201).body(itemService.saveItemFromDTO(itemDTO));
    }

    @PatchMapping("/edit")
    public ResponseEntity<ItemDTO> editTown(@RequestHeader(value = "itemId") Long id,
                                            @RequestBody ItemDTO itemDTO) {
        return ResponseEntity.ok().body(itemService.editItemById(id, itemDTO));
    }

}
