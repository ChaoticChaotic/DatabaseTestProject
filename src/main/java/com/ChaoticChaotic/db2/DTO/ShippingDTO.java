package com.ChaoticChaotic.db2.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingDTO {

    private Long id;
    @FutureOrPresent(message = "Invalid Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Future(message = "Invalid Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private TownDTO fromTown;
    private TownDTO toTown;
    private List<ItemDTO> items;
}
