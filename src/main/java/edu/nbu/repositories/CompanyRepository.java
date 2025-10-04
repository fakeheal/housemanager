package edu.nbu.repositories;

import edu.nbu.entities.Company;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.Valid;

@Repository
public interface CompanyRepository extends CrudRepository<@Valid Company, Long> {
}
