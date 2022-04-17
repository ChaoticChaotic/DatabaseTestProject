package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.entity.Item;
import java.util.List;

public interface ItemService {
    ItemDTO saveItemFromDTO(ItemDTO itemDto);
    void deleteItem(Long id);
    List<Item> showItems();
}
