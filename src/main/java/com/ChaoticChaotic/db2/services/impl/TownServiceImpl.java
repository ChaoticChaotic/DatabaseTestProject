package com.ChaoticChaotic.db2.services.impl;


import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.DTO.mappers.TownMapper;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.NotFoundException;
import com.ChaoticChaotic.db2.repository.TownRepository;
import com.ChaoticChaotic.db2.services.TownService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;
    private TownMapper mapper;


    @Override
    public TownDTO saveFromDTO(TownDTO townDTO) {
        return Optional.of(mapper.createFromDTO(townDTO))
                .filter(town -> townRepository.findByName(townDTO.getName()).isEmpty())
                .map(townRepository::save)
                .map(mapper::returnDTO)
                .orElseThrow(() -> new BadRequestException("Town with name "
                        + townDTO.getName()
                        + " already exists"));
    }

    @Override
    public void delete(Long id) {
        townRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Town with id " + id + " does not exists"));
        townRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TownDTO> show() {
        return Optional.of(townRepository.findAll())
                .map(mapper::returnListDTO)
                .orElse(Collections.emptyList());
    }

    @Override
    public Town findByName(String name) {
        return townRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Town with name " + name + " not found"));
    }

    @Override
    public TownDTO editById(Long id, TownDTO townDTO) {
        return townRepository.findById(id)
                .map(town -> {
                    if(townRepository.findByName(townDTO.getName()).isPresent()) {
                        throw new BadRequestException("Town with name " + townDTO.getName() + " already exists");
                    }
                    return town;
                })
                .map(town -> mapper.mapFromDTO(townDTO, town))
                .map(townRepository::save)
                .map(mapper::returnDTO)
                .orElseThrow(() -> new NotFoundException("Town with id " + id + " does not exists"));
    }

}
