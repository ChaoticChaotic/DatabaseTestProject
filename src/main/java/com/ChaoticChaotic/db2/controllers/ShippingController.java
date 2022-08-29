package com.ChaoticChaotic.db2.controllers;

import com.ChaoticChaotic.db2.DTO.ShippingCreationRequest;
import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ShippingMapper;
import com.ChaoticChaotic.db2.services.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/shipping")
public class ShippingController {

    private ShippingService shippingService;
    private ShippingMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<ShippingDTO>> getAll(){
        return ResponseEntity.ok().body(shippingService.showAll());
    }

    @PostMapping("/save")
    public ResponseEntity<ShippingDTO> save(@RequestBody @Valid ShippingCreationRequest request){
        return ResponseEntity.status(201).body(shippingService.saveFromRequest(request));
    }

    @DeleteMapping("/delete/{shippingId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "shippingId") Long id){
        shippingService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/edit/{shippingId}")
    public ResponseEntity<ShippingDTO> edit(@PathVariable(value = "shippingId") Long id,
                                            @RequestBody @Valid ShippingCreationRequest request)  {
        return ResponseEntity.ok().body(shippingService.editById(id, request));
    }

}
