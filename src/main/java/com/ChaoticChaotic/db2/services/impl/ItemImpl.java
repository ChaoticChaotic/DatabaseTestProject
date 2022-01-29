package com.ChaoticChaotic.db2.services.impl;

import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.IdNotFoundException;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.ChaoticChaotic.db2.repository.ShippingRepository;
import com.ChaoticChaotic.db2.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ItemImpl implements ItemService {

    private ItemRepository itemRepository;
    private ShippingRepository shippingRepository;

    public void addItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        if(!itemRepository.existsById(id)) {
            throw new IdNotFoundException(
                    "Line with id " + id + " does not exists");
        }
        if(shippingRepository.existsById(id)) {
            throw new BadRequestException(
                    "Line with id " + id + " is busy");
        }
        itemRepository.deleteById(id);
    }

    public List<Item> showItems() {
        return itemRepository.findAll();
    }

}
