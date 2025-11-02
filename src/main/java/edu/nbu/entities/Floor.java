package edu.nbu.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer name;

    @ManyToOne
    private Building building;

    @OneToMany(mappedBy = "floor")
    private List<Apartment> apartments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }
}
