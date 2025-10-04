package edu.nbu.entities;

import jakarta.persistence.*;

@Entity
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer name;

    @ManyToOne
    private Building building;
}
