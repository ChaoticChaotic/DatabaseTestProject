package com.ChaoticChaotic.db2.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "shipping")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id", nullable = false)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "town",referencedColumnName = "town_id")
    private Town town;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "items", referencedColumnName = "item_id")
    private Item item;

    public Shipping(Long id, LocalDate startDate, LocalDate endDate, Town town, Item item) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.item = item;
        this.town = town;
    }

    public Shipping(LocalDate startDate, LocalDate endDate, Town town, Item item) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.item = item;
        this.town = town;
    }

    public Shipping() {
    }


    public Long getShippingId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate start_date) {
        this.startDate = start_date;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipping shipping = (Shipping) o;
        return Objects.equals(id, shipping.id)
                && Objects.equals(item, shipping.item)
                && Objects.equals(town, shipping.town)
                && Objects.equals(startDate, shipping.startDate)
                && Objects.equals(endDate, shipping.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, item, town, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Shippings{" +
                "shipping_id=" + id +
                ", item=" + item +
                ", town=" + town +
                ", start_date=" + startDate +
                ", end_date=" + endDate +
                '}';
    }
}
