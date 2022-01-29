package com.ChaoticChaotic.db2.repository;

import com.ChaoticChaotic.db2.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

}
