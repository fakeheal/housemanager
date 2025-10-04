package edu.nbu.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Floor floor;

    private Integer area;
    private Integer numberOfResidents;

    private Integer numberOfPets;
}
