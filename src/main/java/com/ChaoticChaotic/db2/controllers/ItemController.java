package com.ChaoticChaotic.db2.controllers;


import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ItemMapper;
import com.ChaoticChaotic.db2.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private ItemService itemService;
    private ItemMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<ItemDTO>> getAll(){
      return ResponseEntity.ok().body(itemService.show());
    }

    @PostMapping("/save")
    public ResponseEntity<ItemDTO> save(@RequestBody ItemDTO itemDTO){
        return ResponseEntity.status(201).body(itemService.saveFromDTO(itemDTO));
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "itemId") Long id){
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/edit/{itemId}")
    public ResponseEntity<ItemDTO> edit(@PathVariable(value = "itemId") Long id,
                                        @RequestBody ItemDTO itemDTO) {
        return ResponseEntity.ok().body(itemService.editById(id, itemDTO));
    }

}
