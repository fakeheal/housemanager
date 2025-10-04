package edu.nbu.services;

import edu.nbu.entities.Employee;
import edu.nbu.repositories.EmployeeRepository;
import jakarta.inject.Inject;

public class EmployeeService {
    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    CompanyService companyService;

    public Employee createEmployee(String firstName, String lastName) {
        Employee employee = new Employee();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setCompany(companyService.getCompany());

        return employeeRepository.save(employee);
    }
}
