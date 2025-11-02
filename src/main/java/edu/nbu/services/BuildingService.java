package edu.nbu.services;

import edu.nbu.entities.Building;
import edu.nbu.entities.Employee;
import edu.nbu.exceptions.crud.CannotCreateResourceException;
import edu.nbu.exceptions.crud.CannotRetrieveResourceException;
import edu.nbu.repositories.BuildingRepository;
import jakarta.inject.Inject;

public class BuildingService {
    @Inject
    BuildingRepository buildingRepository;

    @Inject
    EmployeeService employeeService;


    public Building create(String name, String address, Long employeeId, float commonArea) {
        Employee employee = employeeService.findById(employeeId);

        if (employee.getBuilding() != null) {
            throw new CannotCreateResourceException("Employee already manages a building");
        }

        Building building = new Building();
        building.setName(name);
        building.setAddress(address);
        building.setCommonArea(commonArea);
        building.setEmployee(employee);

        buildingRepository.save(building);

        return building;
    }

public Building update(Long id, String name, String address, Long employeeId, float commonArea) {
    Building building = this.findById(id);

    Employee newEmployee = employeeService.findById(employeeId);

    if (newEmployee.getBuilding() != null && !newEmployee.getBuilding().getId().equals(id)) {
        throw new CannotCreateResourceException("Employee already manages a building");
    }

    Employee oldEmployee = building.getEmployee();
    oldEmployee.setBuilding(null);

    building.setName(name);
    building.setAddress(address);
    building.setCommonArea(commonArea);
    building.setEmployee(newEmployee);

    buildingRepository.save(building);

    return building;
}

public Building findById(Long id) {
    return buildingRepository.findById(id)
            .orElseThrow(() -> new CannotRetrieveResourceException("Building not found"));
}

    public java.util.List<Building> list() {
        return buildingRepository.findAll();
    }
}
