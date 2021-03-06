package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.DTO.ShippingCreationRequest;
import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.entity.Shipping;

import java.util.List;


public interface ShippingService {
    ShippingDTO saveShippingFromRequest(ShippingCreationRequest request);
    void deleteShippingById(Long id);
    List<Shipping> showAllShipping();
    ShippingDTO editShippingById(Long id, ShippingCreationRequest request);
}
