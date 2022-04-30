package com.ChaoticChaotic.db2.DTO.mappers;

import com.ChaoticChaotic.db2.DTO.ShippingCreationRequest;
import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.services.ItemService;
import com.ChaoticChaotic.db2.services.TownService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ShippingMapper {

    private TownService townService;
    private ItemService itemService;
    private ItemMapper itemMapper;
    private TownMapper townMapper;

    public Shipping createFromRequest(ShippingCreationRequest request) {
        return Shipping.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .fromTown(townService.findTownByName(request.getFromTown()))
                .toTown(townService.findTownByName(request.getToTown()))
                .items(request.getItemIds().stream()
                        .map(itemId -> itemService.findItemById(itemId))
                        .collect(Collectors.toList()))
                .build();
    }

    public Shipping mapFromRequest(ShippingCreationRequest request, Shipping existedShipping) {
        existedShipping.setStartDate(request.getStartDate());
        existedShipping.setEndDate(request.getEndDate());
        existedShipping.setFromTown(townService.findTownByName(request.getFromTown()));
        existedShipping.setToTown(townService.findTownByName(request.getToTown()));
        existedShipping.setItems(request.getItemIds().stream()
                        .map(itemId -> itemService.findItemById(itemId))
                        .collect(Collectors.toList()));
        return existedShipping;
    }

    public ShippingDTO returnDTO(Shipping existedShipping) {
        return ShippingDTO.builder()
                .id(existedShipping.getId())
                .startDate(existedShipping.getStartDate())
                .endDate(existedShipping.getEndDate())
                .fromTown(townMapper.returnDTO(existedShipping.getFromTown()))
                .toTown(townMapper.returnDTO(existedShipping.getToTown()))
                .items(existedShipping.getItems().stream()
                        .map(itemMapper::returnDTOInShipping)
                        .collect(Collectors.toList()))
                .build();
    }
}
