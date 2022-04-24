package com.ChaoticChaotic.db2.DTO.mappers;

import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.repository.TownRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShippingMapper {

    private TownRepository townRepository;
    private ItemMapper itemMapper;

    public Shipping createFromDTO(ShippingDTO shippingDTO) {
        return Shipping.builder()
                .startDate(shippingDTO.getStartDate())
                .endDate(shippingDTO.getEndDate())
                .fromTown(townRepository.findByName(shippingDTO.getFromTown()).get())
                .toTown(townRepository.findByName(shippingDTO.getToTown()).get())
                .items(shippingDTO.getItems())
                .build();
    }

    public Shipping mapFromDTO(ShippingDTO shippingDTO, Shipping existedShipping) {
        existedShipping.setId(shippingDTO.getId());
        existedShipping.setStartDate(shippingDTO.getStartDate());
        existedShipping.setEndDate(shippingDTO.getEndDate());
        existedShipping.setFromTown(townRepository.findByName(shippingDTO.getFromTown()).get());
        existedShipping.setToTown(townRepository.findByName(shippingDTO.getToTown()).get());
        existedShipping.setItems(shippingDTO.getItems());
        return existedShipping;
    }

    public ShippingDTO returnDTO(Shipping existedShipping) {
        return ShippingDTO.builder()
                .id(existedShipping.getId())
                .startDate(existedShipping.getStartDate())
                .endDate(existedShipping.getEndDate())
                .fromTown(existedShipping.getFromTown().getName())
                .toTown(existedShipping.getToTown().getName())
                .items(existedShipping.getItems())
                .build();
    }
}
