package com.ChaoticChaotic.db2.services.impl;

import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.IdNotFoundException;
import com.ChaoticChaotic.db2.repository.ShippingRepository;
import com.ChaoticChaotic.db2.services.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShippingImpl implements ShippingService {


    private ShippingRepository shippingRepository;

    public void addShipping(Shipping shipping) {
    if (!shipping.getStartDate().isBefore(shipping.getEndDate())){
            throw new BadRequestException(
                    "Incorrect shipping dates!"
            );
        }
        shippingRepository.save(shipping);
    }

    public void deleteShipping(Long id) {
        if(!shippingRepository.existsById(id)) {
            throw new IdNotFoundException(
                    "Line with id " + id + " does not exists");
        }
        shippingRepository.deleteById(id);
    }

    public List<Shipping> showShipping() {
        return shippingRepository.findAll();
    }

}
