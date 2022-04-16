package com.ChaoticChaotic.db2.DTO.mappers;

import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item createFromDTO(ItemDTO itemDTO) {
        return Item.builder()
                .name(itemDTO.getName())
                .quantity(itemDTO.getQuantity())
                .build();
    }

    public Item mapFromDTO(ItemDTO itemDTO, Item existedItem) {
        existedItem.setId(itemDTO.getId());
        existedItem.setName(itemDTO.getName());
        existedItem.setQuantity(itemDTO.getQuantity());
        return existedItem;
    }

    public ItemDTO returnDTO(Item existedItem) {
        return ItemDTO.builder()
                .id(existedItem.getId())
                .name(existedItem.getName())
                .quantity(existedItem.getQuantity())
                .build();
    }
}
