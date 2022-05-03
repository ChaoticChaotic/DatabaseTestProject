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
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/shipping")
public class ShippingController {

    private ShippingService shippingService;
    private ShippingMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<ShippingDTO>> getAllShipping(){
        return ResponseEntity.ok().body(shippingService.showAllShipping()
                .stream()
                .map(mapper::returnDTO)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteShipping(@RequestHeader(value = "shippingId") Long id){
        shippingService.deleteShippingById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    public ResponseEntity<ShippingDTO> saveShipping(@RequestBody @Valid ShippingCreationRequest request){
        return ResponseEntity.status(201).body(shippingService.saveShippingFromRequest(request));
    }

    @PatchMapping("/edit")
    public ResponseEntity<ShippingDTO> editShipping(@RequestHeader(value = "shippingId") Long id,
                                                    @RequestBody @Valid ShippingCreationRequest request)  {
        return ResponseEntity.ok().body(shippingService.editShippingById(id, request));
    }

}
