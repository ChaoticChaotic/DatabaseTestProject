package com.ChaoticChaotic.db2.DTO.mappers;

import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.entity.Town;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TownMapper {

    public Town createFromDTO(TownDTO townDTO) {
        return Town.builder()
                .name(townDTO.getName())
                .distance(townDTO.getDistance())
                .build();
    }

    public Town mapFromDTO(TownDTO townDTO, Town existedTown) {
        existedTown.setName(townDTO.getName());
        existedTown.setDistance(townDTO.getDistance());
        return existedTown;
    }

    public TownDTO returnDTO(Town existedTown) {
        return TownDTO.builder()
                .name(existedTown.getName())
                .distance(existedTown.getDistance())
                .build();
    }

    public List<TownDTO> returnListDTO(List<Town> towns) {
        return towns.stream().map(this::returnDTO).collect(Collectors.toList());
    }
}
