package com.ChaoticChaotic.db2.services.impl;


import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.exception.IdNotFoundException;
import com.ChaoticChaotic.db2.repository.TownRepository;
import com.ChaoticChaotic.db2.services.TownService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class TownImpl implements TownService {

    private TownRepository townRepository;

    public void addTown(Town town) {
        townRepository.save(town);
    }

    public void deleteTown(Long id) {
        if(!townRepository.existsById(id)) {
            throw new IdNotFoundException(
                    "Line with id " + id + " does not exists");
        }
        townRepository.deleteById(id);
    }

    public List<Town> showTowns() {
        return townRepository.findAll();
    }

}
