package edu.nbu.entities;

import jakarta.persistence.*;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Floor floor;

    private Float area;
    private Integer numberOfResidents;

    private Integer numberOfPets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Integer getNumberOfResidents() {
        return numberOfResidents;
    }

    public void setNumberOfResidents(Integer numberOfResidents) {
        this.numberOfResidents = numberOfResidents;
    }

    public Integer getNumberOfPets() {
        return numberOfPets;
    }

    public void setNumberOfPets(Integer numberOfPets) {
        this.numberOfPets = numberOfPets;
    }
}
