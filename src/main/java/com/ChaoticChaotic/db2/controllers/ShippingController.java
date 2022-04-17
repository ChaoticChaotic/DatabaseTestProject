package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.services.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/shipping")
public class ShippingController {

    private ShippingService shippingService;


    @GetMapping("/all")
    private List<Shipping> showShipping(){
        return shippingService.showAllShipping();
    }

    @DeleteMapping("/delete/{id}")
    private void deleteTown(@PathVariable("id") Long id){
        shippingService.deleteShippingById(id);
    }

    @PostMapping("/save")
    private ResponseEntity<ShippingDTO> saveShipping(@RequestBody ShippingDTO shippingDTO){
        return ResponseEntity.status(201).body(shippingService.saveShippingFromDTO(shippingDTO));
    }

}
