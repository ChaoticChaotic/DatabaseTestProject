package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.DTO.ShippingCreationRequest;
import com.ChaoticChaotic.db2.DTO.ShippingDTO;

import java.util.List;


public interface ShippingService {
    ShippingDTO saveFromRequest(ShippingCreationRequest request);
    void deleteById(Long id);
    List<ShippingDTO> showAll();
    ShippingDTO editById(Long id, ShippingCreationRequest request);
}
