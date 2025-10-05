package edu.nbu.services;

import edu.nbu.entities.Employee;
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

    public List<Employee> list() {
        return employeeRepository.findAll();
    }
}
