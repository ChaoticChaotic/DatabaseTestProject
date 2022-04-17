package com.ChaoticChaotic.db2.services.impl;

import com.ChaoticChaotic.db2.DTO.ItemDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ItemMapper;
import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.NotFoundException;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.ChaoticChaotic.db2.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ItemImpl implements ItemService {

    private ItemRepository itemRepository;
    private ItemMapper mapper;

    @Override
    public ItemDTO saveItemFromDTO(ItemDTO itemDTO) {
        return Optional.of(mapper.createFromDTO(itemDTO))
                .map(itemRepository::save)
                .map(mapper::returnDTO)
                .orElseThrow(() -> new BadRequestException("something went wrong"));
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Line with id " + id + " does not exists"));
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> showItems() {
        return Optional.of(itemRepository.findAll())
                .orElse(Collections.emptyList());
    }

}
