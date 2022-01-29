package com.ChaoticChaotic.db2.entity;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "town_id", nullable = false)
    private Long id;

    @Column(name = "town_name", length = 30)
    private String name;

    @Column(name = "distance", nullable = false)
    private Long distance;


    public Town() {
    }

    public Town(String name, Long distance) {
        this.name = name;
        this.distance = distance;
    }

    public Town(Long id, String name, Long distance) {
        this.id = id;
        this.name = name;
        this.distance = distance;
    }


    public Long getTownId() {
        return id;
    }

    public String getTownName() {
        return name;
    }

    public void setTownName(String town_name) {
        this.name = town_name;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Town town = (Town) o;
        return Objects.equals(id, town.id) && Objects.equals(name, town.name) && Objects.equals(distance, town.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, distance);
    }

    @Override
    public String toString() {
        return "Towns{" +
                "town_id=" + id +
                ", town_name='" + name + '\'' +
                ", distance=" + distance +
                '}';
    }
}
