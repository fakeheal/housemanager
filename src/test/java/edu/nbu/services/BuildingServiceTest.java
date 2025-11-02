package edu.nbu.services;

import edu.nbu.entities.Building;
import edu.nbu.entities.Employee;
import edu.nbu.repositories.BuildingRepository;
import edu.nbu.repositories.CompanyRepository;
import edu.nbu.repositories.EmployeeRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
class BuildingServiceTest {
    @Inject
    BuildingService buildingService;

    @Inject
    BuildingRepository buildingRepository;

    @Inject
    CompanyService companyService;

    @Inject
    CompanyRepository companyRepository;

    @Inject
    EmployeeService employeeService;

    @Inject
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        companyService.create("Test Company", "123 Test St, Test City, TC 12345");
    }

    @AfterEach
    void tearDown() {
        buildingRepository.deleteAll();
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    void create() {
        Employee employee = employeeService.create("John", "Doe");
        Building building = buildingService.create("Test Building", "456 Test Ave, Test City, TC 67890", employee.getId(), 50.0f, 10.00f, 20.00f, 5.00f);
        assert building != null;

        assert building.getName().equals("Test Building");
        assert building.getAddress().equals("456 Test Ave, Test City, TC 67890");
        assert building.getEmployee().getId().equals(employee.getId());
        assert buildingRepository.count() == 1;
    }

    @Test
    void create_throwsException_whenEmployeeAlreadyManagesBuilding() {
        Employee employee = employeeService.create("John", "Doe");
        buildingService.create("Test Building", "456 Test Ave, Test City, TC 67890", employee.getId(), 50.0f, 10.00f, 20.00f, 5.00f);

        assert buildingRepository.count() == 1;

        try {
            buildingService.create("Another Building", "789 Another St, Another City, AC 12345", employee.getId(), 30.0f, 10.00f, 20.00f, 5.00f);
        } catch (Exception e) {
            assert e.getMessage().contains("Employee already manages a building");
        }

        assert buildingRepository.count() == 1;
    }

    @Test
    void update() {
        Employee employee1 = employeeService.create("John", "Doe");
        Building building = buildingService.create("Test Building", "456 Test Ave, Test City, TC 67890", employee1.getId(), 50.0f, 10.00f, 20.00f, 5.00f);

        Employee employee2 = employeeService.create("Jane", "Smith");
        Building updatedBuilding = buildingService.update(building.getId(), "Updated Building", "789 Updated St, Updated City, UC 54321", employee2.getId(), 75.0f, 10.00f, 20.00f, 5.00f);

        assert updatedBuilding != null;
        assert updatedBuilding.getName().equals("Updated Building");
        assert updatedBuilding.getAddress().equals("789 Updated St, Updated City, UC 54321");
        assert updatedBuilding.getEmployee().getId().equals(employee2.getId());
        assert buildingRepository.count() == 1;
    }

    @Test
    void update_throwsException_whenEmployeeAlreadyManagesBuilding() {
        Employee employee1 = employeeService.create("John", "Doe");
        Building building1 = buildingService.create("Test Building 1", "456 Test Ave, Test City, TC 67890", employee1.getId(), 50.0f, 10.00f, 20.00f, 5.00f);

        Employee employee2 = employeeService.create("Jane", "Smith");
        Building building2 = buildingService.create("Test Building 2", "789 Another St, Another City, AC 12345", employee2.getId(), 30.0f, 10.00f, 20.00f, 5.00f);

        try {
            buildingService.update(building2.getId(), "Updated Building 2", "101 Updated St, Updated City, UC 54321", employee1.getId(), 75.0f, 10.00f, 20.00f, 5.00f);
        } catch (Exception e) {
            assert e.getMessage().contains("Employee already manages a building");
        }

        assert buildingRepository.count() == 2;
    }
}