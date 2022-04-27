package com.ChaoticChaotic.db2.DTO.mappers;

import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.ChaoticChaotic.db2.repository.TownRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ShippingMapper {

    private TownRepository townRepository;
    private ItemRepository itemRepository;
    private ItemMapper itemMapper;
    private TownMapper townMapper;

    public Shipping createFromDTO(ShippingDTO shippingDTO) {
        return Shipping.builder()
                .startDate(shippingDTO.getStartDate())
                .endDate(shippingDTO.getEndDate())
                .fromTown(townRepository.findByName(shippingDTO.getFromTown().getName())
                        .orElseThrow(() -> new BadRequestException("Town with name " +
                                shippingDTO.getFromTown().getName() +
                                " not found")))
                .toTown(townRepository.findByName(shippingDTO.getFromTown().getName())
                        .orElseThrow(() -> new BadRequestException("Town with name " +
                                shippingDTO.getFromTown().getName() +
                                " not found")))
                .items(shippingDTO.getItems().stream()
                        .map(itemDTO -> itemRepository.findByName(itemDTO.getName())
                                .orElseThrow(() -> new BadRequestException("Item not found")))
                        .collect(Collectors.toList()))
                .build();
    }

    public Shipping mapFromDTO(ShippingDTO shippingDTO, Shipping existedShipping) {
        existedShipping.setId(shippingDTO.getId());
        existedShipping.setStartDate(shippingDTO.getStartDate());
        existedShipping.setEndDate(shippingDTO.getEndDate());
        existedShipping.setFromTown(townRepository.findByName(shippingDTO.getFromTown().getName())
                .orElseThrow(() -> new BadRequestException("Town with name " +
                        shippingDTO.getFromTown().getName() +
                        " not found")));
        existedShipping.setToTown(townRepository.findByName(shippingDTO.getToTown().getName())
                .orElseThrow(() -> new BadRequestException("Town with name " +
                        shippingDTO.getFromTown().getName() +
                        " not found")));
        existedShipping.setItems(shippingDTO.getItems().stream()
                .map(itemDTO -> itemRepository.findByName(itemDTO.getName())
                        .orElseThrow(() -> new BadRequestException("Item not found")))
                .collect(Collectors.toList()));
        return existedShipping;
    }

    public ShippingDTO returnDTO(Shipping existedShipping) {
        return ShippingDTO.builder()
                .id(existedShipping.getId())
                .startDate(existedShipping.getStartDate())
                .endDate(existedShipping.getEndDate())
                .fromTown(townMapper.returnDTO(existedShipping.getFromTown()))
                .toTown(townMapper.returnDTO(existedShipping.getFromTown()))
                .items(existedShipping.getItems().stream()
                        .map(itemMapper::returnDTOInShipping)
                        .collect(Collectors.toList()))
                .build();
    }
}
