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
        existedTown.setId(townDTO.getId());
        existedTown.setName(townDTO.getName());
        existedTown.setDistance(townDTO.getDistance());
        return existedTown;
    }
}
