package edu.nbu.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @ManyToOne
    private Company owner;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Floor> floors;
}
