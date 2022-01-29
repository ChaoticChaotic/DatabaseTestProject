package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.entity.Item;
import java.util.List;

public interface ItemService {
    void deleteItem(Long id);
    List<Item> showItems();
    void addItem(Item item);
}
