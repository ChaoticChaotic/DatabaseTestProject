package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.DTO.TownDTO;
import com.ChaoticChaotic.db2.entity.Town;

import java.util.List;

public interface TownService {
    TownDTO saveTownFromDTO(TownDTO townDTO);
    void deleteTown(Long id);
    List<Town> showTowns();
    Town findTownByName(String name);
    TownDTO editTownById(Long id, TownDTO townDTO);
}
