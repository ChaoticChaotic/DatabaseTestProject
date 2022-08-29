package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.entity.Item;
import java.util.List;

public interface ItemService {
    ItemDTO saveFromDTO(ItemDTO itemDto);
    void delete(Long id);
    List<ItemDTO> show();
    ItemDTO editById(Long id, ItemDTO itemDTO);
    Item findById(Long id);
}
