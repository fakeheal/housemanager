package edu.nbu.services;

import edu.nbu.entities.Building;
import edu.nbu.entities.Employee;
import edu.nbu.exceptions.crud.CannotCreateResourceException;
import edu.nbu.repositories.BuildingRepository;
import jakarta.inject.Inject;

public class BuildingService {
    @Inject
    BuildingRepository buildingRepository;

    @Inject
    EmployeeService employeeService;


    public Building create(String name, String address, Long employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if (employee.getBuilding() != null) {
            throw new CannotCreateResourceException("Employee already manages a building");
        }

        Building building = new Building();
        building.setName(name);
        building.setAddress(address);
        building.setOwner(employee);

        buildingRepository.save(building);

        return building;
    }

}
