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
                .fromTown(shippingDTO.getFromTown())
                .toTown(shippingDTO.getToTown())
                .items(shippingDTO.getItems())
                .build();
    }

    public Shipping mapFromDTO(ShippingDTO shippingDTO, Shipping existedShipping) {
        existedShipping.setId(shippingDTO.getId());
        existedShipping.setStartDate(shippingDTO.getStartDate());
        existedShipping.setEndDate(shippingDTO.getEndDate());
        existedShipping.setFromTown(shippingDTO.getFromTown());
        existedShipping.setToTown(shippingDTO.getToTown());
        existedShipping.setItems(shippingDTO.getItems());
        return existedShipping;
    }

    public ShippingDTO returnDTO(Shipping existedShipping) {
        return ShippingDTO.builder()
                .id(existedShipping.getId())
                .startDate(existedShipping.getStartDate())
                .endDate(existedShipping.getEndDate())
                .fromTown(existedShipping.getFromTown())
                .toTown(existedShipping.getToTown())
                .items(existedShipping.getItems())
                .build();
    }
}
