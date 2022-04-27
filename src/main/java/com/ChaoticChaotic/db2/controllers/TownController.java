package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.DTO.mappers.TownMapper;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.services.TownService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/town")
public class TownController {

    private TownService townService;
    private TownMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<TownDTO>> getAllTowns(){
        return ResponseEntity.ok().body(townService.showTowns()
                .stream()
                .map(mapper::returnDTO)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteTown(@RequestHeader(value = "townId") Long id){
        townService.deleteTown(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    public ResponseEntity<TownDTO> addTown(@RequestBody TownDTO townDTO){
        return ResponseEntity.status(201).body(townService.saveTownFromDTO(townDTO));
    }

    @PatchMapping("/edit")
    public ResponseEntity<TownDTO> editTown(@RequestHeader(value = "townId") Long id,
                                             @RequestBody TownDTO townDTO) {
        return ResponseEntity.ok().body(townService.editTownById(id, townDTO));
    }
}
