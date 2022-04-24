package com.ChaoticChaotic.db2.DTO.mappers;

import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.entity.Town;
import org.springframework.stereotype.Component;

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
                .id(existedTown.getId())
                .name(existedTown.getName())
                .distance(existedTown.getDistance())
                .build();
    }
}
