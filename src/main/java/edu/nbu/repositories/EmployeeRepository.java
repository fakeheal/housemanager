package edu.nbu.repositories;

import edu.nbu.entities.Employee;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.Valid;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<@Valid Employee, Long> {
    List<Employee> findByBuildingIsNull();
}
