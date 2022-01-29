package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.entity.Shipping;

import java.util.List;


public interface ShippingService {
    void addShipping(Shipping shipping);
    void deleteShipping(Long id);
    List<Shipping> showShipping();
}
