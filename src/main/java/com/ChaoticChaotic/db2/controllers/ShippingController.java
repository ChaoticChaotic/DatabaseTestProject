package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.services.ShippingService;
import com.ChaoticChaotic.db2.services.impl.ShippingImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ShippingController {

    private ShippingService shippingService;


    @GetMapping("/shippings")
    private List<Shipping> showShipping(){
        return shippingService.showShipping();
    }

    @DeleteMapping("/shippings/{id}")
    private void deleteTown(@PathVariable("id") Long id){
        shippingService.deleteShipping(id);
    }

    @PostMapping("/shippings")
    private Long addShipping(@RequestBody Shipping shipping){
        shippingService.addShipping(shipping);
        return shipping.getShippingId();
    }

}
