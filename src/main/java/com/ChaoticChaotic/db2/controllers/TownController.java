package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.services.TownService;
import com.ChaoticChaotic.db2.services.impl.TownImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Long addTown(@RequestBody Town town){
        townService.addTown(town);
        return town.getTownId();
    }
}
