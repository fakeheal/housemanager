package edu.nbu.services;

import edu.nbu.entities.Employee;
import edu.nbu.exceptions.crud.CannotDeleteResourceException;
import edu.nbu.repositories.EmployeeRepository;
import jakarta.inject.Inject;

import java.util.List;

public class EmployeeService {
    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    CompanyService companyService;

    public void create(String firstName, String lastName) {
        Employee employee = new Employee();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setCompany(companyService.getCompany());

        employeeRepository.save(employee);
    }

    public void delete(Long id) {

        //@TODO: Prevent deleting an employee when their building cannot be assigned to someone else

        if (employeeRepository.count() == 1)
            throw new CannotDeleteResourceException("Cannot delete the last employee in the company");

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new CannotDeleteResourceException("Employee with id " + id + " not found"));


        //@TODO: Assign its buildings to someone else
        employeeRepository.delete(employee);
    }

    public List<Employee> list() {
        return employeeRepository.findAll();
    }
}
