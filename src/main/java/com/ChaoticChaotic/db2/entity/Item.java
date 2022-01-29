package com.ChaoticChaotic.db2.entity;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Long id;

    @Column(name = "item_name", length = 30)
    private String name;

    @Column(name = "quantity",nullable = false )
    private Long quantity;


    public Item() {
    }

    public Item(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Item(Long id, String name, Long quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(quantity, item.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity);
    }

    @Override
    public String toString() {
        return "Items{" +
                "item_id=" + id +
                ", item_name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
