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

    private Float commonArea;

    @OneToOne
    private Employee employee;

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Floor> floors;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;

        if (employee != null && employee.getBuilding() != this) {
            employee.setBuilding(this);
        }
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public Float getCommonArea() {
        return commonArea;
    }

    public void setCommonArea(Float commonArea) {
        this.commonArea = commonArea;
    }
}
