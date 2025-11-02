package edu.nbu.services;

import edu.nbu.entities.Apartment;
import edu.nbu.entities.Building;
import edu.nbu.entities.Employee;
import edu.nbu.repositories.*;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;


@MicronautTest
class ApartmentServiceTest {

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

    Building building;

    @BeforeEach
    void setUp() {
        companyService.create("Test Company", "123 Test St, Test City, TC 12345");
        Employee employee = employeeService.create("John", "Doe");
        building = buildingService.create("Test Building", "456 Building Ave, Build City, BC 67890", employee.getId(), 100.0f);


    }

    @AfterEach
    void tearDown() {
        apartmentRepository.deleteAll();
        floorRepository.deleteAll();
        buildingRepository.deleteAll();
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void create() {
        Apartment apartment = apartmentService.create(building.getId(), "Apt 101", 1, 75.0f, 3, 1);

        assert floorRepository.findByBuildingAndName(building, 1).isPresent();
        assert apartment.getName().equals("Apt 101");

        assert apartmentRepository.count() == 1;
    }

    @Test
    public void create_throwsException_whenApartmentNameExistsOnFloor() {
        apartmentService.create(building.getId(), "Apt 101", 1, 75.0f, 3, 1);

        boolean exceptionThrown = false;
        try {
            apartmentService.create(building.getId(), "Apt 101", 1, 80.0f, 2, 0);
        } catch (Exception e) {
            exceptionThrown = true;
            assert Objects.equals(e.getMessage(), "Apartment with the given name already exists on the specified floor.");
        }

        assert exceptionThrown;
        assert apartmentRepository.count() == 1;
    }

    @Test
    public void list() {
        apartmentService.create(building.getId(), "Apt 101", 1, 75.0f, 3, 1);
        apartmentService.create(building.getId(), "Apt 102", 1, 80.0f, 2, 0);
        apartmentService.create(building.getId(), "Apt 201", 2, 90.0f, 4, 2);

        var apartments = apartmentService.list(building.getId());

        assert apartments.size() == 3;
    }

    @Test
    public void list_returnsEmptyList_whenNoApartmentsExist() {
        var apartments = apartmentService.list(building.getId());

        assert apartments.isEmpty();
    }

    @Test
    public void list_returnsApartmentsOnlyForSpecifiedBuilding() {
        apartmentService.create(building.getId(), "Apt 101", 1, 75.0f, 3, 1);

        Building anotherBuilding = buildingService.create("Another Building", "789 Another St, Another City, AC 10112", employeeService.create("Jane", "Smith").getId(), 150.0f);
        apartmentService.create(anotherBuilding.getId(), "Apt 201", 2, 85.0f, 2, 0);

        var apartments = apartmentService.list(building.getId());

        assert apartments.size() == 1;
        assert apartments.getFirst().getName().equals("Apt 101");
    }

    @Test
    public void update() {
        Apartment apartment = apartmentService.create(building.getId(), "Apt 101", 1, 75.0f, 3, 1);

        Apartment updatedApartment = apartmentService.update(apartment.getId(), "Apt 101A", 2, 80.0f, 4, 2);

        assert updatedApartment.getName().equals("Apt 101A");
        assert updatedApartment.getArea() == 80.0f;
        assert updatedApartment.getNumberOfResidents() == 4;
        assert updatedApartment.getNumberOfPets() == 2;

        assert apartmentRepository.count() == 1;
        assert floorRepository.findByBuildingAndName(building, 2).isPresent();
        assert floorRepository.count() == 1;
    }

    @Test
    public void update_throwsException_whenApartmentNameExistsOnFloor() {
        Apartment apartment1 = apartmentService.create(building.getId(), "Apt 101", 1, 75.0f, 3, 1);
        apartmentService.create(building.getId(), "Apt 102", 1, 80.0f, 2, 0);

        boolean exceptionThrown = false;
        try {
            apartmentService.update(apartment1.getId(), "Apt 102", 1, 75.0f, 3, 1);
        } catch (Exception e) {
            exceptionThrown = true;
            assert Objects.equals(e.getMessage(), "Apartment with the given name already exists on the specified floor.");
        }

        assert exceptionThrown;
    }
}