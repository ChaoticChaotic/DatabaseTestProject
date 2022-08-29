package com.ChaoticChaotic.db2.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "town")
public class Town {

    @SequenceGenerator(
            name = "town_sequence",
            sequenceName = "town_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "town_sequence"
    )
    @Column(nullable = false)
    private Long id;
    @Column(name = "town_name", length = 30)
    private String name;
    @Column(name = "distance", nullable = false)
    private Long distance;
}
