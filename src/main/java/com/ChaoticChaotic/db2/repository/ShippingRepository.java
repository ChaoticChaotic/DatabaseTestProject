package com.ChaoticChaotic.db2.repository;

import com.ChaoticChaotic.db2.entity.Shipping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping,Long> {

}
