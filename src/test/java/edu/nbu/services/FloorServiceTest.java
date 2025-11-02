package edu.nbu.services;

import edu.nbu.entities.Building;
import edu.nbu.entities.Employee;
import edu.nbu.repositories.BuildingRepository;
import edu.nbu.repositories.CompanyRepository;
import edu.nbu.repositories.EmployeeRepository;
import edu.nbu.repositories.FloorRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@MicronautTest
class FloorServiceTest {

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

    @Inject
    FloorService floorService;

    @Inject
    FloorRepository floorRepository;

    Building building;

    @BeforeEach
    void setUp() {
        companyService.create("Test Company", "123 Test St, Test City, TC 12345");
        Employee employee = employeeService.create("John", "Doe");
        building = buildingService.create("Test Building", "456 Building Ave, Build City, BC 67890", employee.getId(), 100.0f);


    }

    @AfterEach
    void tearDown() {
        buildingRepository.deleteAll();
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        floorRepository.deleteAll();
    }

    @Test
    public void firstOrCreate() {
        floorService.firstOrCreate(building, 1);

        assert floorRepository.findByBuildingAndName(building, 1).isPresent();

        floorService.firstOrCreate(building, 1);

        assert floorRepository.count() == 1;
    }
}