package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.services.TownService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TownController {


    private TownService townService;

    @GetMapping("/towns")
    private List<Town> showTowns(){
        return townService.showTowns();
    }

    @DeleteMapping("/towns/{id}")
    private void deleteTown(@PathVariable("id") Long id){
        townService.deleteTown(id);
    }

    @PostMapping("/towns")
    private ResponseEntity<TownDTO> addTown(@RequestBody TownDTO townDTO){
        return ResponseEntity.status(201).body(townService.saveTownFromDTO(townDTO));
    }
}
