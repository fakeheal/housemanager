package edu.nbu.services;

import edu.nbu.entities.Apartment;
import edu.nbu.entities.Building;
import edu.nbu.entities.Employee;
import edu.nbu.entities.Fee;
import edu.nbu.repositories.*;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
class FeeServiceTest {

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

    @Inject
    ApartmentService apartmentService;

    @Inject
    ApartmentRepository apartmentRepository;

    @Inject
    FeeService feeService;

    @Inject
    FeeRepository feeRepository;

    Building building;

    @BeforeEach
    void setUp() {
        companyService.create("Test Company", "123 Test St, Test City, TC 12345");
        Employee employee = employeeService.create("John", "Doe");
        building = buildingService.create("Test Building", "456 Building Ave, Build City, BC 67890", employee.getId(), 100.0f, 10.00f, 20.00f, 5.00f);


    }

    @AfterEach
    void tearDown() {
        feeRepository.deleteAll();
        apartmentRepository.deleteAll();
        floorRepository.deleteAll();
        buildingRepository.deleteAll();
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void issueFees() {
        Apartment apartment = apartmentService.create(building.getId(), "Apt 101", 1, 75.0f, 3, 1);
        feeService.issueFees(java.time.YearMonth.of(2025, 10), building.getId());

        Fee fee = feeRepository.findByApartmentFloorBuildingIdAndPeriod(
                building.getId(),
                java.time.YearMonth.of(2025, 10)
        ).getFirst();

        assert fee.getApartment().getId().equals(apartment.getId());
        assert fee.getAmount() == (int) (((75.0f * 10.00f) + (3 * 20.00f) + (1 * 5.00f)) * 100);
    }

}