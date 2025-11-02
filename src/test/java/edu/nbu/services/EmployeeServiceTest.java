package edu.nbu.services;

import edu.nbu.entities.Building;
import edu.nbu.entities.Employee;
import edu.nbu.repositories.CompanyRepository;
import edu.nbu.repositories.EmployeeRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@MicronautTest
class EmployeeServiceTest {

    @Inject
    EmployeeService employeeService;

    @Inject
    CompanyService companyService;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    CompanyRepository companyRepository;

    @Inject
    BuildingService buildingService;

    @BeforeEach
    void setUp() {
        companyService.create("Test Company", "123 Test St, Test City, TC 12345");
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    void create() {
        String firstName = "John";
        String lastName = "Doe";

        employeeService.create(firstName, lastName);
        var employees = employeeService.list();
        assert employees
                .stream()
                .anyMatch(e -> e.getFirstName().equals(firstName) && e.getLastName().equals(lastName));

        assertEquals(1, employeeRepository.count());

        assertEquals(1, companyService.findById().getEmployees().size());
    }

    @Test
    void delete() {
        String firstName = "John";
        String lastName = "Doe";

        Employee employee = employeeService.create(firstName, lastName);
        assertEquals(1, employeeRepository.count());

        String firstName2 = "Jane";
        String lastName2 = "Smith";
        employeeService.create(firstName2, lastName2);
        assertEquals(2, employeeRepository.count());

        employeeService.delete(employee.getId());
        assertEquals(1, employeeRepository.count());
    }

    @Test
    void delete_throwsException_whenDeletingLastEmployee() {
        String firstName = "John";
        String lastName = "Doe";

        Employee employee = employeeService.create(firstName, lastName);
        assertEquals(1, employeeRepository.count());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeService.delete(employee.getId());
        });

        String expectedMessage = "Cannot delete the last employee in the company";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(1, employeeRepository.count());
    }

    @Test
    void delete_throwsException_whenEmployeeNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeService.delete(999L);
        });

        String expectedMessage = "Employee with id 999 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete_throwsException_whenEmployeeOwnsBuildingAndNoOtherEmployee() {
        String firstName = "John";
        String lastName = "Doe";

        Employee employee = employeeService.create(firstName, lastName);
        assertEquals(1, employeeRepository.count());
        Building building = buildingService.create("Building 1", "456 Building St, Build City, BC 67890", employee.getId(), 1500.0f, 10.00f, 20.00f, 5.00f);

        String firstName2 = "Jane";
        String lastName2 = "Smith";
        Employee employee2 = employeeService.create(firstName2, lastName2);
        assertEquals(2, employeeRepository.count());
        Building building2 = buildingService.create("Building 2", "789 Another St, Another City, AC 10112", employee2.getId(), 2000.0f,10.00f, 20.00f, 5.00f);

        // Simulate that the employee owns a building by ensuring there are no other employees without buildings
        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeService.delete(employee.getId());
        });

        String expectedMessage = "Cannot delete employee with id " + employee.getId() + " because they own a building and there is no other employee to assign it to.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(2, employeeRepository.count());
    }

    @Test
    void list() {
        String firstName1 = "John";
        String lastName1 = "Doe";

        String firstName2 = "Jane";
        String lastName2 = "Smith";

        employeeService.create(firstName1, lastName1);
        employeeService.create(firstName2, lastName2);

        var employees = employeeService.list();
        assertEquals(2, employees.size());
        assert employees.stream().anyMatch(e -> e.getFirstName().equals(firstName1) && e.getLastName().equals(lastName1));
        assert employees.stream().anyMatch(e -> e.getFirstName().equals(firstName2) && e.getLastName().equals(lastName2));
    }

    @Test
    void findById() {
        String firstName = "John";
        String lastName = "Doe";

        Employee createdEmployee = employeeService.create(firstName, lastName);
        assertNotNull(createdEmployee.getId());

        Employee foundEmployee = employeeService.findById(createdEmployee.getId());
        assertNotNull(foundEmployee);
        assertEquals(createdEmployee.getId(), foundEmployee.getId());
        assertEquals(createdEmployee.getFirstName(), foundEmployee.getFirstName());
        assertEquals(createdEmployee.getLastName(), foundEmployee.getLastName());
    }
}