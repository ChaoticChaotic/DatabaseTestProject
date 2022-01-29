package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.entity.Town;

import java.util.List;

public interface TownService {
    void addTown(Town town);
    void deleteTown(Long id);
    List<Town> showTowns();
}
