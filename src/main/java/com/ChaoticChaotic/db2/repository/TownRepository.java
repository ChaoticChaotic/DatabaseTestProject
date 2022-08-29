package com.ChaoticChaotic.db2.repository;

import com.ChaoticChaotic.db2.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town,Long> {
    Optional<Town> findByName(String name);
}
