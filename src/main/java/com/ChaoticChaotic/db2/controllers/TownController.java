package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.DTO.mappers.TownMapper;
import com.ChaoticChaotic.db2.services.TownService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/town")
public class TownController {

    private TownService townService;
    private TownMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<TownDTO>> getAll(){
        return ResponseEntity.ok().body(townService.show());
    }

    @PostMapping("/save")
    public ResponseEntity<TownDTO> save(@RequestBody TownDTO townDTO){
        return ResponseEntity.status(201).body(townService.saveFromDTO(townDTO));
    }

    @DeleteMapping("/delete/{townId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "townId") Long id){
        townService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/edit/{townId}")
    public ResponseEntity<TownDTO> edit(@PathVariable(value = "townId") Long id,
                                        @RequestBody TownDTO townDTO) {
        return ResponseEntity.ok().body(townService.editById(id, townDTO));
    }
}
