package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.entity.Town;

import java.util.List;

public interface TownService {
    TownDTO saveFromDTO(TownDTO townDTO);
    void delete(Long id);
    List<TownDTO> show();
    Town findByName(String name);
    TownDTO editById(Long id, TownDTO townDTO);
}
