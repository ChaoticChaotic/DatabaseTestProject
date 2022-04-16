package com.ChaoticChaotic.db2.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private Long id;
    private String name;
    private Long quantity;
}
