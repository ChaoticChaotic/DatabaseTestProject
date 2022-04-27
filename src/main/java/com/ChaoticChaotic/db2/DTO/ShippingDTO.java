package com.ChaoticChaotic.db2.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private TownDTO fromTown;
    private TownDTO toTown;
    private List<ItemDTO> items;
}
