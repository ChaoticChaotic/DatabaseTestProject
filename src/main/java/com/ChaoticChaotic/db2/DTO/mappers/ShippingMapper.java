package com.ChaoticChaotic.db2.DTO.mappers;

import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.entity.Shipping;
import org.springframework.stereotype.Component;

@Component
public class ShippingMapper {
    public Shipping createFromDTO(ShippingDTO shippingDTO) {
        return Shipping.builder()
                .startDate(shippingDTO.getStartDate())
                .endDate(shippingDTO.getEndDate())
                .towns(shippingDTO.getTowns())
                .items(shippingDTO.getItems())
                .build();
    }

    public Shipping mapFromDTO(ShippingDTO shippingDTO, Shipping existedShipping) {
        existedShipping.setId(shippingDTO.getId());
        existedShipping.setStartDate(shippingDTO.getStartDate());
        existedShipping.setEndDate(shippingDTO.getEndDate());
        existedShipping.setTowns(shippingDTO.getTowns());
        existedShipping.setItems(shippingDTO.getItems());
        return existedShipping;
    }

    public ShippingDTO returnDTO(Shipping existedShipping) {
        return ShippingDTO.builder()
                .id(existedShipping.getId())
                .startDate(existedShipping.getStartDate())
                .endDate(existedShipping.getEndDate())
                .towns(existedShipping.getTowns())
                .build();
    }
}
