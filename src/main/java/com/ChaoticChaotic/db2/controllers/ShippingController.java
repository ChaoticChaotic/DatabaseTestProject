package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ShippingMapper;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.services.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/shipping")
public class ShippingController {

    private ShippingService shippingService;
    private ShippingMapper mapper;

    @GetMapping("/all")
    private ResponseEntity<List<ShippingDTO>> showShipping(){
        return ResponseEntity.ok().body(shippingService.showAllShipping()
                .stream()
                .map(mapper::returnDTO)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Object> deleteTown(@PathVariable("id") Long id){
        shippingService.deleteShippingById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    private ResponseEntity<ShippingDTO> saveShipping(@RequestBody ShippingDTO shippingDTO){
        return ResponseEntity.status(201).body(shippingService.saveShippingFromDTO(shippingDTO));
    }

}
