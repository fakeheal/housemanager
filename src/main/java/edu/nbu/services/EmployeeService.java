package edu.nbu.services;

import edu.nbu.entities.Employee;
import edu.nbu.exceptions.crud.CannotDeleteResourceException;
import edu.nbu.exceptions.crud.CannotRetrieveResourceException;
import edu.nbu.repositories.EmployeeRepository;
import jakarta.inject.Inject;

import java.util.List;

public class EmployeeService {
    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    CompanyService companyService;

    public Employee create(String firstName, String lastName) {
        Employee employee = new Employee();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setCompany(companyService.findById());

        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new CannotDeleteResourceException("Employee with id " + id + " not found"));

        if (employeeRepository.count() == 1) {
            throw new CannotDeleteResourceException("Cannot delete the last employee in the company");
        }

        if (employeeRepository.findByBuildingIsNull().isEmpty()) {
            throw new CannotDeleteResourceException("Cannot delete employee with id " + id + " because they own a building and there is no other employee to assign it to.");
        }

        //@TODO: Assign its buildings to someone else
        employeeRepository.delete(employee);
    }

    public List<Employee> list() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CannotRetrieveResourceException("Employee with id " + employeeId + " not found"));
    }
}
