package com.ChaoticChaotic.db2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shipping")
public class Shipping {

    @SequenceGenerator(
            name = "shipping_sequence",
            sequenceName = "shipping_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "shipping_sequence"
    )
    @Column(nullable = false)
    private Long id;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Town fromTown;
    @ManyToOne(fetch = FetchType.LAZY)
    private Town toTown;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Item> items;
}
