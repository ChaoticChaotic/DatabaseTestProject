package com.ChaoticChaotic.db2.services.impl;

import com.ChaoticChaotic.db2.DTO.ShippingCreationRequest;
import com.ChaoticChaotic.db2.DTO.ShippingDTO;
import com.ChaoticChaotic.db2.DTO.mappers.ShippingMapper;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.NotFoundException;
import com.ChaoticChaotic.db2.repository.ShippingRepository;
import com.ChaoticChaotic.db2.services.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShippingImpl implements ShippingService {
    
    private ShippingRepository shippingRepository;
    private ShippingMapper mapper;

    @Override
    public ShippingDTO saveShippingFromDTO(ShippingCreationRequest request) {
        return Optional.of(mapper.createFromRequest(request))
                .filter(shipping -> shipping.getStartDate().isBefore(shipping.getEndDate()))
                .map(shippingRepository::save)
                .map(mapper::returnDTO)
                .orElseThrow(() -> new BadRequestException("Incorrect shipping dates!"));
    }

    @Override
    public void deleteShippingById(Long id) {
        shippingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shipping with id " + id + " does not exists"));
        shippingRepository.deleteById(id);
    }

    @Override
    public List<Shipping> showAllShipping() {
        return Optional.of(shippingRepository.findAll())
                .orElse(Collections.emptyList());
    }

    @Override
    public ShippingDTO editShippingById(Long id, ShippingCreationRequest request) {
        return shippingRepository.findById(id)
                .map(shipping -> mapper.mapFromRequest(request, shipping))
                .map(shippingRepository::save)
                .map(mapper::returnDTO)
                .orElseThrow(() -> new NotFoundException("Shipping with id " + id + " does not exists"));
    }

}
